package com.game.studio.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.studio.models.persons.Player;

public interface PlayerDao extends JpaRepository<Player, Long> {

}
