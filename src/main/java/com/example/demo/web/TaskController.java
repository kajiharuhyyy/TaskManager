package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
		model.addAttribute("taskForm", new TaskForm());
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
	
	@GetMapping("/tasks/{id}/edit")
	public String editTaskForm(@PathVariable Long id, Model model) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid task ID"));
		
		TaskForm form = new TaskForm();
		form.setId(task.getId());
		form.setTitle(task.getTitle());
		form.setAssigneeId(task.getAssignee().getId());
		form.setDueDate(task.getDueDate());
		form.setStatus(task.getStatus().name());
		form.setWorkHours(task.getWorkHours());
		
		model.addAttribute("taskForm", form);
		model.addAttribute("members", memberRepository.findAll());
		model.addAttribute("statuses", TaskStatus.values());
		
		return "tasks/edit";
	}
	
	@GetMapping("/tasks/{id}")
	public String showTask(@PathVariable Long id, Model model) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + id));
		
		model.addAttribute("task", task);
		
		return "tasks/show";
		}
	
	@PostMapping("/tasks/{id}/edit")
	public String updateTask(@PathVariable Long id, @ModelAttribute TaskForm taskForm) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + id));
		
		Member assignee = memberRepository.findById(taskForm.getAssigneeId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
		
		task.setTitle(taskForm.getTitle());
		task.setAssignee(assignee);
		task.setDueDate(taskForm.getDueDate());
		task.setStatus(TaskStatus.valueOf(taskForm.getStatus()));
		task.setWorkHours(taskForm.getWorkHours());
		
		taskRepository.save(task);
		
		return "redirect:/tasks";
	}
	
	@PostMapping("/tasks/{id}/delete")
	public String deleteTask(@PathVariable Long id) {
		taskRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + id));
		
		taskRepository.deleteById(id);
		
		System.out.println("** deletedTask 呼ばれたid=" + id);
		
		return "redirect:/tasks";
	}
}
