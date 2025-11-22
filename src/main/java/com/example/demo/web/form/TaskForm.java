package com.example.demo.web.form;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class TaskForm {
	
	private Long id;

	private String title;
	private Long assigneeId;
	private LocalDate dueDate;
	private String status;
	private BigDecimal workHours;
}
