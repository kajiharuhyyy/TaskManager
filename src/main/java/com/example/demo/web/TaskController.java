package com.example.demo.web;

import org.springframework.stereotype.Controller;

import com.example.demo.repo.TaskRepository;

@Controller
@RequiresArgsConstructor
public class TaskController {

	private final TaskRepository taskRepository;
}
