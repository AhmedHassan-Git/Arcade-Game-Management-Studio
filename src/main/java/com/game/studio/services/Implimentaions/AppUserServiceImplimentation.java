package com.game.studio.services.Implimentaions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.game.studio.dao.AppRoleDao;
import com.game.studio.dao.AppUserDao;
import com.game.studio.models.user.AppRole;
import com.game.studio.models.user.AppUser;
import com.game.studio.services.AppUserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImplimentation implements AppUserService, UserDetailsService {
    private final AppUserDao userDao;
    private final AppRoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userDao.findByUserName(username);
        if (appUser == null) {
            log.error("User not Found");
            throw new UsernameNotFoundException("User noot found in te database");
        } else {
            log.info("User Found: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(appUser.getUserName(), appUser.getPassword(),
                authorities);
    }

    @Override
    public AppUser persist(AppUser entity) {
        System.out.println("Saving new user to the database" + entity.getName());
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return userDao.save(entity);
    }

    @Override
    public List<AppUser> findAll() {
        return userDao.findAll();
    }

    @Override
    public AppUser findByUsername(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public void addRole(String username, String roleName) {
        AppUser user = userDao.findByUserName(username);
        AppRole role = roleDao.findByName(roleName);
        user.getRoles().add(role);

    }

    @Override
    public AppRole saveRole(AppRole entity) {
        log.info("Saving new role {} to the database", entity.getName());
        return roleDao.save(entity);
    }

}
