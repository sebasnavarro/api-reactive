package com.lilblue.demo.services;

import com.lilblue.demo.documents.Player;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlayerService {

    public Flux<Player> findAll();

    public Mono<Player> findById(String id);

    public Mono<Player> save(Player player);

    public Mono<Void> delete(Player player);
}