package com.game.studio.services.Implimentaions;

import java.util.List;

import org.springframework.stereotype.Service;

import com.game.studio.dao.GameDao;
import com.game.studio.models.game.Game;
import com.game.studio.services.GameService;

import com.game.studio.exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GameServiceImplimentation implements GameService {

    private GameDao dao;

    @Override
    public Game persist(Game entity) {
        return dao.save(entity);
    }

    @Override
    public List<Game> findAll() {
        return dao.findAll();
    }

    @Override
    public Game findById(long id) {
        return dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game", "Id", id));
    }

    @Override
    public Game update(Game entity, long id) {
        Game temp = dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Player", "Id", id));
        temp.setA(entity.getA());
        temp.setP1(entity.getP1());
        temp.setP2(entity.getP2());
        temp.setWinner(entity.getWinner());
        dao.save(temp);
        return temp;
    }

    @Override
    public void delete(long id) {
        dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game", "Id", id));
        dao.deleteById(id);
    }

}
