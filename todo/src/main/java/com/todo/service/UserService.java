package com.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.model.User;
import com.todo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public User getUserByUserId(Long id) {
		return userRepository.findById(id).get();
	}

	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username).get();
	}

	public User createUser(User user) {
		return userRepository.save(user);
	}

	public User updateUser(User user) {
		return userRepository.save(user);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
	
	public boolean existsByUsernameAndEmail(String username, String email) {
		return userRepository.existsByUsernameAndEmail(username, email);
	}
}
