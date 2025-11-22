package com.example.demo.web.form;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TaskForm {
	
	private Long id;

	@NotBlank(message = "タスク名は必須項目です。")
	private String title;
	
	@NotNull(message = "担当者を選択してください。")
	private Long assigneeId;
	
	@FutureOrPresent(message = "期限日は今日以降の日付を指定してください。")
	private LocalDate dueDate;
	
	@NotBlank(message = "状態を選択してください。")
	private String status;
	
	@DecimalMin(value = "0.0", inclusive = true, message = "作業時間は0以上でなければなりません。")
	private BigDecimal workHours;
}
