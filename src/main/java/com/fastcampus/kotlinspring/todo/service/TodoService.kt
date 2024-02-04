package com.fastcampus.kotlinspring.todo.service

import com.fastcampus.kotlinspring.todo.api.model.TodoRequest
import com.fastcampus.kotlinspring.todo.domain.Todo
import com.fastcampus.kotlinspring.todo.domain.TodoRepository
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.domain.Sort.by
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class TodoService(
    private val todoRepository: TodoRepository
) {

    @Transactional(readOnly = true)
    fun findAll(): List<Todo> =
        todoRepository.findAll(by(Direction.DESC, "id"))

    @Transactional(readOnly = true)
    fun findById(id: Long): Todo =
        todoRepository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @Transactional
    fun create(request: TodoRequest?): Todo {
        checkNotNull(request) { "TodoRequest is null" } // null이 아닌 경우 non-null 타입으로 자동 추론됨.

        val todo = Todo(
            title = request.title,
            description = request.description,
            done = request.done,
            createdAt = LocalDateTime.now()
        )

        return todoRepository.save(todo)
    }

    @Transactional
    fun update(id: Long, request: TodoRequest?): Todo {
        checkNotNull(request) { "TodoRequest is null" } // null이 아닌 경우 non-null 타입으로 자동 추론됨.

        return findById(id).let {
            it.update(request.title, request.description, request.done)
            todoRepository.save(it)
        }
    }

    @Transactional
    fun delete(id: Long) = todoRepository.deleteById(id)
}