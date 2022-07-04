package com.game.studio.services.Implimentaions;

import java.util.List;

import org.springframework.stereotype.Service;

import com.game.studio.dao.PlayerDao;
import com.game.studio.exceptions.ResourceNotFoundException;
import com.game.studio.models.persons.Player;
import com.game.studio.services.PlayerService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PlayerServiceImplimentation implements PlayerService {

    private PlayerDao dao;

    @Override
    public Player persist(Player entity) {

        return dao.save(entity);
    }

    @Override
    public List<Player> findAll() {
        return dao.findAll();
    }

    @Override
    public Player findById(long id) {
        return dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Player", "Id", id));
    }

    @Override
    public Player update(Player entity, long id) {
        Player temp = dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Player", "Id", id));
        temp.setName(entity.getName());
        temp.setTag(entity.getTag());
        temp.setTotal(entity.getTotal());
        temp.setPaid(entity.getPaid());
        dao.save(temp);
        return temp;
    }

    @Override
    public void delete(long id) {
        dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Player", "Id", id));
        dao.deleteById(id);
    }

}
