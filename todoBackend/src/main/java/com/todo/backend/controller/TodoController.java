package com.todo.backend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.sun.jersey.multipart.FormDataParam;
import com.todo.backend.exception.ResourceNotFoundException;
import com.todo.backend.model.Todo;
import com.todo.backend.repository.TodoRepository;

@RestController
@RequestMapping("/restApi/v1")
public class TodoController {
	
	@Autowired
    private TodoRepository todoRepository;
	
	@GetMapping("/todos")
    public List<Todo> getAllTodos() {
       return todoRepository.findAll();
    }
	
	@GetMapping("/todos/filter")
	public List<Todo> searchTodos(@RequestParam(required = false) Long id,
			@RequestParam(required = false) String descripcion, @RequestParam(required = false) Boolean estado) {
		return todoRepository.searchByFilter(id, descripcion, estado);
	}

    @PostMapping("/todos")
    public Todo createTodo(@FormDataParam("descripcion") String descripcion, @FormDataParam("file") MultipartFile file) {
    	Todo todo = new Todo();
    	try {
	    	todo.setDescripcion(descripcion);
	    	todo.setEstado(Boolean.FALSE);
	    	if(null != file) {
	    		byte [] byteArr=file.getBytes();
				todo.setAdjunto(byteArr);
	    	}
			
			return todoRepository.save(todo);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
    }
    
    @PutMapping("/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable(value = "id") Long todoId, @Valid @RequestBody Todo todoDetails) {
        try {
        	 Todo todo = todoRepository.findById(todoId)
						.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado tarea para este id:: " + todoId));
	        todo.setEstado(todoDetails.getEstado());
	        final Todo updatedTodo = todoRepository.save(todo);
	        return ResponseEntity.ok(updatedTodo);
        } catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
    }
    
}
