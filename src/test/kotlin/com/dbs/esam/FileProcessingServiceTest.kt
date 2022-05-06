package com.dbs.esam

import com.dbs.esam.entity.Adjustments
import com.dbs.esam.entity.FileStatus
import com.dbs.esam.repo.AdjustmentsRepo
import com.dbs.esam.repo.FileStatusRepo
import com.dbs.esam.service.FileProcessService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileProcessingServiceTest {
    @MockBean
    private lateinit var fileStatusRepo: FileStatusRepo

    @MockBean
    private lateinit var adjustmentsRepo: AdjustmentsRepo

    @Autowired
    private lateinit var service: FileProcessService

    @Test
    fun `test file processing`() {
        Mockito.`when`(fileStatusRepo.findByProcessedFalse()).thenReturn(
            listOf(FileStatus(
                "adjustment",
                "adjustment.xlsx",
                "User1"))
        )
        Mockito.`when`(adjustmentsRepo.save(Mockito.any()))
            .thenReturn(Adjustments(1, "header2", "header3", 1))
        Mockito.`when`(fileStatusRepo.save(Mockito.any()))
            .thenReturn(FileStatus(
                "adjustment",
                "adjustment.xlsx",
                "User1"))
        service.processFiles()
    }
}