package com.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.model.Todo;
import com.todo.model.User;
import com.todo.repository.TodoRepository;
import com.todo.repository.UserRepository;

@Service
public class TodoService {
	@Autowired
	private TodoRepository todoRepository;
	@Autowired
	private UserRepository userRepository;

	private User getUserByUsername(String username) {
		return userRepository.findByUsername(username).get();
	}

	public List<Todo> getAllTodos(String username) {
		return todoRepository.findAllByUsername(username);
	}

	public Todo getTodo(String username, Long id) {
		return todoRepository.findById(id).get();
	}

	public void deleteTodo(String username, Long id) {
		todoRepository.deleteById(id);
	}

	public Todo createTodo(String username, Todo todo) {
		User user = getUserByUsername(username);
		todo.getUsers().add(user);
		return todoRepository.save(todo);
	}

	public Todo updateTodo(String username, Long id, Todo todo) {
		User user = getUserByUsername(username);
		todo.getUsers().add(user);
		return todoRepository.save(todo);
	}
}
