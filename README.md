# TaskManager　— タスク管理ミニアプリ

## はじめに
TaskManager は、社内メンバー向けのシンプルなタスク管理アプリです。
タスクの登録・編集・一覧表示ができ、担当者(メンバー)と紐付けて管理できます。

Spring Boot + Thymeleaf + H2 Database の構成で動作する学習用 Web アプリです。

チャットGPTに上司としてタスクを投げてもらい作成しました。

![Task Mail](./images/タスクメール.png)

---

## 機能


### タスク管理
- タスク一覧表示
- タスク新規登録
- タスク編集
- 作業時間（h）の管理
- ステータス（未着手 / 進行中 / 完了）の管理
- 担当者（Member）との紐付け

### メンバー管理
- メンバー一覧
- メンバーの新規登録
- メンバー編集
- メンバー削除

---

## 技術スタック

- **Spring Boot 3.5.7**
- **Thymeleaf**
- **H2 Database**
- **Spring Data JPA / Hibernate**
- **Maven**
- **Java17**

---