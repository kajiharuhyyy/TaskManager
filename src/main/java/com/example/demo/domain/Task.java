package com.example.demo.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // エンティティクラスであることを示すアノテーション
@Table(name = "tasks") // テーブル名を指定するアノテーション
@Getter // ゲッターを自動生成するアノテーション
@Setter // セッターを自動生成するアノテーション
@AllArgsConstructor // 全引数コンストラクタを自動生成するアノテーション
@NoArgsConstructor // 引数なしコンストラクタを自動生成するアノテーション
@Builder // ビルダーパターンを自動生成するアノテーション
public class Task {

	@Id // 主キーを示すアノテーション
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主キーの生成戦略を指定するアノテーション
	private Long id;
	
	@Column(nullable = false, length = 200) // カラムの制約を指定するアノテーション
	private String title;
	
	@Column(length = 1000)
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY) // 多対一のリレーションを示すアノテーション
	@JoinColumn(name = "assignee_id", nullable = false)
	private Member assignee;
	
	private LocalDate dueDate;
	
	@Enumerated(EnumType.STRING) // 列挙型を文字列として保存することを指定するアノテーション
	@Column(nullable = false, length = 20)
	private TaskStatus status;
	
	@Column(precision = 5, scale = 2)
	private BigDecimal workHours;
	
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	@PrePersist // エンティティが永続化される前に実行されるアノテーション
	public void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.updatedAt = now;
		if (this.status == null) {
			this.status = TaskStatus.NOT_STARTED;
		}
	} // エンティティが永続化される前に実行されるメソッド
	
	@PreUpdate // エンティティが更新される前に実行されるアノテーション
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	} // エンティティが更新される前に実行されるメソッド
} 
