package com.game.studio.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.studio.dto.ManagerDto;
import com.game.studio.models.persons.Manager;
import com.game.studio.services.ManagerService;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

	private ManagerService service;

	public ManagerController(ManagerService service) {
		super();
		this.service = service;
	}

	@PostMapping()
	public ResponseEntity<Manager> save(@RequestBody ManagerDto entity) {
        Manager temp = new Manager();
        temp.setName(entity.getName());
        temp.setTag(entity.getTag());
        temp.setHours(entity.getHours());
        temp.setPay(entity.getPay());
		return new ResponseEntity<Manager>(service.persist(temp), HttpStatus.CREATED);
	}

	@GetMapping()
	public List<Manager> findAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Manager> findById(@PathVariable("id") long id) {
		return new ResponseEntity<Manager>(service.findById(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Manager> update(@PathVariable("id") long id, @RequestBody ManagerDto entity) {
        Manager temp = new Manager();
        temp.setName(entity.getName());
        temp.setTag(entity.getTag());
        temp.setHours(entity.getHours());
        temp.setPay(entity.getPay());
		return new ResponseEntity<Manager>(service.update(temp, id), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") long id) {
		service.delete(id);
		return new ResponseEntity<String>("Employee deleted successfully.", HttpStatus.OK);
	}

}
