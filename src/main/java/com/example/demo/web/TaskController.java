package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.Member;
import com.example.demo.domain.Task;
import com.example.demo.domain.TaskStatus;
import com.example.demo.repo.MemberRepository;
import com.example.demo.repo.TaskRepository;
import com.example.demo.web.form.TaskForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TaskController {

	private final TaskRepository taskRepository;
	private final MemberRepository memberRepository;
	
	@GetMapping("/tasks")
	public String listTasks(Model model) {
		model.addAttribute("tasks", taskRepository.findAll());
		return "tasks/list";
	}
	
	@GetMapping("/tasks/new")
	public String newTaskForm(Model model) {
		model.addAttribute("taskForm, new TaskForm()");
		model.addAttribute("members", memberRepository.findAll());
		model.addAttribute("statuses", TaskStatus.values());
		return "tasks/new";
	}
	
	@PostMapping("/tasks")
	public String createTask(@ModelAttribute TaskForm taskForm) {
		
		Member assignee = memberRepository.findById(taskForm.getAssigneeId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
		
		Task task = Task.builder()
				.title(taskForm.getTitle())
				.assignee(assignee)
				.dueDate(taskForm.getDueDate())
				.status(TaskStatus.valueOf(taskForm.getStatus()))
				.workHours(taskForm.getWorkHours())
				.build();
		
		taskRepository.save(task);
		// Implementation for creating a new task
		return "redirect:/tasks";
	}
}
