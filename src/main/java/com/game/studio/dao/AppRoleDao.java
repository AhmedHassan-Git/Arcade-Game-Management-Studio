package com.game.studio.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.studio.models.user.AppRole;

public interface AppRoleDao extends JpaRepository<AppRole, Long> {
    AppRole findByName(String name);
}
