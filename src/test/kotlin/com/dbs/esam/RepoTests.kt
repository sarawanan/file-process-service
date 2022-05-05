package com.dbs.esam

import com.dbs.esam.entity.FileStatus
import com.dbs.esam.repo.AdjustmentsRepo
import com.dbs.esam.repo.FileStatusRepo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class RepoTests @Autowired constructor(
    val testEntityManager: TestEntityManager,
    val fileStatusRepo: FileStatusRepo,
    val adjustmentsRepo: AdjustmentsRepo
) {
    @Test
    fun `test file status save`() {
        testEntityManager.persist(FileStatus(
            "adjustment",
            "adjustment_today.xls",
            "User1"
        ))
        val list: List<FileStatus>? = fileStatusRepo.findByProcessedFalse()
        Assertions.assertNotNull(list)
        org.assertj.core.api.Assertions.assertThat(list?.size == 1)
        list?.let {
            Assertions.assertFalse(it[0].processed)
            Assertions.assertEquals("adjustment", it[0].fileType)
        }
    }

    //TODO: Have other test cases for adjustment repo etc
}