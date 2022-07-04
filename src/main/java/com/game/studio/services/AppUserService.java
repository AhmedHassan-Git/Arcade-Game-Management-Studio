package com.game.studio.services;

import java.util.List;

import com.game.studio.models.user.AppRole;
import com.game.studio.models.user.AppUser;

public interface AppUserService {
    public AppUser persist(AppUser entity);

    public List<AppUser> findAll();

    public AppUser findByUsername(String username);

    public void addRole(String username, String roleName);

    AppRole saveRole(AppRole entity);

}
