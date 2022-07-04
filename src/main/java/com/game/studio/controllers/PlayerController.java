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

import com.game.studio.dto.PlayerDto;
import com.game.studio.models.persons.Player;
import com.game.studio.services.PlayerService;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

	private PlayerService service;

	public PlayerController(PlayerService service) {
		super();
		this.service = service;
	}

	@PostMapping()
	public ResponseEntity<Player> save(@RequestBody PlayerDto entity) {
        Player temp = new Player();
        temp.setName(entity.getName());
        temp.setTag(entity.getTag());
        temp.setTotal(entity.getTotal());
        temp.setPaid(entity.getPaid());
		return new ResponseEntity<Player>(service.persist(temp), HttpStatus.CREATED);
	}

	@GetMapping()
	public ResponseEntity<List<Player>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Player> findById(@PathVariable("id") long id) {
		return new ResponseEntity<Player>(service.findById(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Player> update(@PathVariable("id") long id, @RequestBody PlayerDto entity) {
        Player temp = new Player();
        temp.setName(entity.getName());
        temp.setTag(entity.getTag());
        temp.setTotal(entity.getTotal());
        temp.setPaid(entity.getPaid());
		return new ResponseEntity<Player>(service.update(temp, id), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") long id) {
		service.delete(id);
		return new ResponseEntity<String>("Employee deleted successfully.", HttpStatus.OK);
	}

}
