package com.example.demo.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Member;
import com.example.demo.domain.Task;
import com.example.demo.domain.TaskStatus;

public interface TaskRepository  extends JpaRepository<Task, Long> {
	
	List<Task> findByStatus(TaskStatus status);
	
	List<Task>findByAssignee(Member assigneer);
	
	List<Task> findByAssigneeAndStatus(Member assignee, TaskStatus status);
}
