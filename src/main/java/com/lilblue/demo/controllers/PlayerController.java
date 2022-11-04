package com.lilblue.demo.controllers;

import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;
import com.lilblue.demo.documents.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.lilblue.demo.services.*;

@CrossOrigin(origins = { "http://localhost:3000", "*" })
@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    private PlayerService service;

    @Value("${config.uploads.path}")
    private String path;

    @PostMapping("/upload/{id}")
    public Mono<ResponseEntity<Player>> upload(@PathVariable String id, @RequestPart FilePart file) {
        return service.findById(id).flatMap(p -> {
            p.setPhoto(UUID.randomUUID().toString() + "-" + file.filename()
                    .replace(" ", "")
                    .replaceAll(":", "")
                    .replace("\\", ""));

            return file.transferTo(new File(path + p.getPhoto())).then(service.save(p));
        }).map(p -> ResponseEntity.ok(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<Player>>> lista() {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Player>> ver(@PathVariable String id) {
        return service.findById(id).map(p -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> crear(@Valid @RequestBody Mono<Player> monoPlayer) {

        Map<String, Object> respuesta = new HashMap<String, Object>();
        return monoPlayer.flatMap(player -> {

            return service.save(player).map(p -> {
                respuesta.put("player", p);
                respuesta.put("mensaje", "Successfully created player");
                respuesta.put("timestamp", new Date());
                return ResponseEntity
                        .created(URI.create("/api/player/".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(respuesta);
            });

        }).onErrorResume(t -> {
            return Mono.just(t).cast(WebExchangeBindException.class)
                    .flatMap(e -> Mono.just(e.getFieldErrors()))
                    .flatMapMany(Flux::fromIterable)
                    .map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list -> {
                        respuesta.put("errors", list);
                        respuesta.put("timestamp", new Date());
                        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
                        return Mono.just(ResponseEntity.badRequest().body(respuesta));
                    });
        });

    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Player>> editar(@RequestBody Player player, @PathVariable String id) {
        return service.findById(id).flatMap(p -> {
            p.setName(player.getName());
            p.setLastname(player.getLastname());
            p.setBirthDate(player.getBirthDate());
            return service.save(p);
        }).map(p -> ResponseEntity.created(URI.create("/api/player/".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id) {
        return service.findById(id).flatMap(p -> {
            return service.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
