package com.example.demo.web.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MemberForm {
	
	private Long id;
	
	@NotBlank(message = "名前は必須項目です。")
	private String name;
	
	@NotBlank(message = "メールアドレスは必須項目です。")
	@Email(message = "有効なメールアドレスを入力してください。")
	private String email;
	
	@NotNull(message = "有効/無効を選択してください。")
	private Boolean active;
}
