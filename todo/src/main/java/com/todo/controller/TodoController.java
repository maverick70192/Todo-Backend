package com.todo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.todo.model.Todo;
import com.todo.service.TodoService;

@CrossOrigin(origins = { "*" }, maxAge = 3600)
@RestController
public class TodoController {
	@Autowired
	private TodoService todoService;

	@GetMapping(path = "/jpa/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username) {
		return todoService.getAllTodos(username);
	}

	@GetMapping(path = "/jpa/users/{username}/todos/{id}")
	public Todo getTodo(@PathVariable String username, @PathVariable Long id) {
		return todoService.getTodo(username, id);
	}

	@DeleteMapping(path = "/jpa/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable Long id) {
		todoService.deleteTodo(username, id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping(path = "/jpa/users/{username}/todos")
	public ResponseEntity<Todo> createTodo(@PathVariable String username, @RequestBody Todo todo) {
		Todo createdTodo = todoService.createTodo(username, todo);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTodo.getId())
				.toUri();
		return ResponseEntity.created(uri).body(createdTodo);
	}

	@PutMapping(path = "/jpa/users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable Long id,
			@RequestBody Todo todo) {
		Todo updatedTodo = todoService.updateTodo(username, id, todo);
		return new ResponseEntity<Todo>(updatedTodo, HttpStatus.OK);
	}
}
