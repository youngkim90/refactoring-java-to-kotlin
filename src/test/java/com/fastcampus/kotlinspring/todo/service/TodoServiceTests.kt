package com.fastcampus.kotlinspring.todo.service

import com.fastcampus.kotlinspring.todo.domain.Todo
import com.fastcampus.kotlinspring.todo.domain.TodoRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
class TodoServiceTests {
    @MockkBean
    lateinit var repository: TodoRepository // lateinit을 사용하여 나중에 초기화

    lateinit var service: TodoService

    // by lazy를 사용하여 stub을 사용하는 시점에 초기화
    private val stub: Todo by lazy {
        Todo(
            id = 1,
            title = "테스트",
            description = "테스트 상세",
            done = false,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )
    }

    @BeforeEach
    fun setup() {
        service = TodoService(repository) // mocking된 repository 주입
    }

    @Test
    fun `한개의 TODO를 반환해야 한다`() {
        // given
        every { repository.findByIdOrNull(1) } returns stub // findByIdOrNull(1) 호출 시 stub 반환

        // when
        val actual = service.findById(1L)

        // then
        assertThat(actual).isNotNull
    }
}