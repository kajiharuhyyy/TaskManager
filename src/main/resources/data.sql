CREATE TABLE members (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)    NOT NULL,  -- 表示名（例：山田 太郎）
    email       VARCHAR(255),              -- メールアドレス（あれば）
    active      BOOLEAN         NOT NULL DEFAULT TRUE  -- 退職・異動で使わなくなった場合にOFF
);

CREATE TABLE tasks (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(200)   NOT NULL,        -- タスク名
    description  VARCHAR(1000),                  -- 詳細（必要なら）
    assignee_id  BIGINT         NOT NULL,        -- 担当者（members.id）
    due_date     DATE,                           -- 期限
    status       VARCHAR(20)    NOT NULL,        -- 未着手 / 進行中 / 完了 など（Enum文字列）
    work_hours   DECIMAL(5,2),                   -- 累計作業時間（h）。例：1.50, 0.75
    created_at   TIMESTAMP      NOT NULL,        -- 登録日時
    updated_at   TIMESTAMP      NOT NULL,        -- 更新日時

    CONSTRAINT fk_tasks_assignee
        FOREIGN KEY (assignee_id) REFERENCES members(id)
);

INSERT INTO members (id, name, email, active) VALUES
(1, '山田 太郎', 'taro@example.com', TRUE),
(2, '佐藤 花子', 'hanako@example.com', TRUE),
(3, '中村 一郎', 'ichiro@example.com', TRUE);

