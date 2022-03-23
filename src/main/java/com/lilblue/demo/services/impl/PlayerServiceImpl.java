package com.lilblue.demo.services.impl;

import com.lilblue.demo.dao.PlayerDao;
import com.lilblue.demo.documents.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.lilblue.demo.services.*;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerDao dao;

    @Override
    public Flux<Player> findAll() {
        return dao.findAll();
    }

    @Override
    public Mono<Player> findById(String id) {
        return dao.findById(id);
    }

    @Override
    public Mono<Player> save(Player player) {
        return dao.save(player);
    }

    @Override
    public Mono<Void> delete(Player player) {
        return dao.delete(player);
    }
}
