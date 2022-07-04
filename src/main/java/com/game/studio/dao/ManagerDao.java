package com.game.studio.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.studio.models.persons.Manager;

public interface ManagerDao extends JpaRepository<Manager, Long> {

}
