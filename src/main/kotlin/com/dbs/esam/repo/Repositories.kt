package com.dbs.esam.repo

import com.dbs.esam.entity.Adjustments
import com.dbs.esam.entity.FileStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FileStatusRepo : JpaRepository<FileStatus, Long> {
    fun findByProcessedFalse(): List<FileStatus>?
}

@Repository
interface AdjustmentsRepo : JpaRepository<Adjustments, Long>