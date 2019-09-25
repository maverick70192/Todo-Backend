package com.todo.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.todo.model.User;
import com.todo.service.UserService;

@CrossOrigin(origins = { "*" }, maxAge = 3600)
@RestController
@RequestMapping(path = { "/jpa/users" })
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder encoder;

	@GetMapping(path = { "/" })
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping(path = { "/{username}" })
	public User getUserByUsername(@PathVariable String username) {
		return userService.getUserByUsername(username);
	}

	@PostMapping(path = { "/" })
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		User createdUser = userService.createUser(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId())
				.toUri();
		return ResponseEntity.created(uri).body(createdUser);
	}
	
	@GetMapping(path = { "/exists" })
	public Boolean isUserExistsByUsernameAndEmail(@Valid @RequestBody User user) {
		return userService.existsByUsernameAndEmail(user.getUsername(), user.getEmail());
	}

	@PutMapping(path = { "/{username}" })
	public ResponseEntity<User> updateUser(@PathVariable String username, @Valid @RequestBody User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		User updatedUser = userService.updateUser(user);
		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
	}

	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);

		return ResponseEntity.noContent().build();
	}
}
