package com.fastcampus.kotlinspring.todo.api.model

import com.fastcampus.kotlinspring.todo.domain.Todo
import com.fasterxml.jackson.annotation.JsonIgnore

data class TodoListResponse(
    val items: List<TodoResponse>,
) {
    val size: Int
        @JsonIgnore // JSON으로 변환 시 제외
        get() = items.size

    fun get(index: Int) = items[index]

    // static 함수 대체
    companion object {
        fun of(todoList: List<Todo>) =
            TodoListResponse(todoList.map(TodoResponse::of))
    }
}