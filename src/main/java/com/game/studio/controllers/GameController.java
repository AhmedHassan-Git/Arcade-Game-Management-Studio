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

import com.game.studio.dto.GameDto;
import com.game.studio.models.game.Game;
import com.game.studio.models.persons.Person;
import com.game.studio.services.GameService;
import com.game.studio.services.PlayerService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/game")
@AllArgsConstructor
public class GameController {

	private GameService service;
    private PlayerService playerService;

	@PostMapping()
	public ResponseEntity<Game> save(@RequestBody GameDto entity) {
        Person p1 = playerService.findById(entity.getP1Id());
        Person p2 = playerService.findById(entity.getP2Id());
        Person winner = playerService.findById(entity.getWinnerId());
        Game temp = new Game(null, entity.getArcade(), p1, p2, winner);
		return new ResponseEntity<Game>(service.persist(temp), HttpStatus.CREATED);
	}

	@GetMapping()
	public List<Game> findAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Game> findById(@PathVariable("id") long id) {
		return new ResponseEntity<Game>(service.findById(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Game> update(@PathVariable("id") long id, @RequestBody GameDto entity) {
        Person p1 = playerService.findById(entity.getP1Id());
        Person p2 = playerService.findById(entity.getP2Id());
        Person winner = playerService.findById(entity.getWinnerId());
        Game temp = new Game(null, entity.getArcade(), p1, p2, winner);
		return new ResponseEntity<Game>(service.update(temp, id), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") long id) {
		service.delete(id);
		return new ResponseEntity<String>("Employee deleted successfully.", HttpStatus.OK);
	}

}
