package com.game.studio.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.studio.models.game.Game;

public interface GameDao extends JpaRepository<Game, Long> {

}
