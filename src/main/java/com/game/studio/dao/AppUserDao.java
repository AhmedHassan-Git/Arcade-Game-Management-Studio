package com.game.studio.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.studio.models.user.AppUser;

public interface AppUserDao extends JpaRepository<AppUser, Long> {
    AppUser findByUserName(String username);
}
