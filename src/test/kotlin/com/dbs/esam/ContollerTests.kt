package com.dbs.esam

import com.dbs.esam.client.S3Client
import com.dbs.esam.repo.FileStatusRepo
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest
class FileProcessControllerTest(val mockMvc: MockMvc) {
    @MockBean
    private lateinit var s3Client: S3Client

    @MockBean
    private lateinit var repo: FileStatusRepo

    @Test
    fun `test file upload`() {
        every { repo.findByProcessedFalse()}
    }
}