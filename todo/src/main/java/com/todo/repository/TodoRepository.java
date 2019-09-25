package com.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.todo.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
	@Query(nativeQuery = true, value = "select t.id, t.description, t.is_done, t.target_date from todo as t inner join todos_users as tu on t.id = tu.todo_id inner join users as u on u.id = tu.user_id where u.username = ?")
	List<Todo> findAllByUsername(String username);
}
