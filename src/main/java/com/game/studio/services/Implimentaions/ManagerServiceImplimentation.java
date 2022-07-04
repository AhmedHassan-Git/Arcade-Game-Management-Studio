package com.game.studio.services.Implimentaions;

import java.util.List;

import org.springframework.stereotype.Service;

import com.game.studio.dao.ManagerDao;
import com.game.studio.models.persons.Manager;
import com.game.studio.services.ManagerService;

import com.game.studio.exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ManagerServiceImplimentation implements ManagerService {

    private ManagerDao dao;

    @Override
    public Manager persist(Manager entity) {

        return dao.save(entity);
    }

    @Override
    public List<Manager> findAll() {
        return dao.findAll();
    }

    @Override
    public Manager findById(long id) {
        return dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Manager", "Id", id));
    }

    @Override
    public Manager update(Manager entity, long id) {
        Manager temp = dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Manager", "Id", id));
        temp.setName(entity.getName());
        temp.setTag(entity.getTag());
        temp.setHours(entity.getHours());
        temp.setPay(entity.getPay());
        dao.save(temp);
        return temp;
    }

    @Override
    public void delete(long id) {
        dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Manager", "Id", id));
        dao.deleteById(id);
    }

}
