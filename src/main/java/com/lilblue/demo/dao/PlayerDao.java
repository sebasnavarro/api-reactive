package com.lilblue.demo.dao;

import com.lilblue.demo.documents.Player;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PlayerDao extends ReactiveMongoRepository<Player, String> {

}
