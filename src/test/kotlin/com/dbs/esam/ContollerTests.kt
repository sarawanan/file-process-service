package com.dbs.esam

import com.dbs.esam.client.S3Client
import com.dbs.esam.entity.FileStatus
import com.dbs.esam.repo.FileStatusRepo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WebMvcTest
class FileProcessControllerTest @Autowired constructor(val mockMvc: MockMvc) {
    @MockBean
    private lateinit var s3Client: S3Client

    @MockBean
    private lateinit var repo: FileStatusRepo

    @Test
    fun `test file upload`() {
        Mockito.`when`(repo.save(any()))
            .thenReturn(FileStatus("adjustment", "adjustment.xlsx", "User1"))
        val file = MockMultipartFile("file", "test-file".toByteArray())
        mockMvc.perform(multipart("/api/file/upload/adjustment")
            .file(file))
            .andExpect(status().isOk)
    }
}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DashboardController @Autowired constructor(val template: TestRestTemplate) {
    @MockBean
    private lateinit var repo: FileStatusRepo

    @Test
    fun `test dashboard`() {
        Mockito.`when`(repo.findAll()).thenReturn(listOf(
            FileStatus("adjustment",
                "adjustment.xlsx",
                "user1",
                LocalDateTime.now(),
                false,
                1)))
        val entity = template.getForEntity<String>("/api/dashboard")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("<td>adjustment.xlsx</td>")
        assertThat(entity.body).contains("<td>Failed</td>")
    }
}