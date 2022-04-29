package com.dbs.esam.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class FileStatus(
    var fileType: String,
    var fileName: String,
    var uploadedBy: String,
    var uploadedAt: LocalDateTime = LocalDateTime.now(),
    var processed: Boolean = false,
    @Id @GeneratedValue var id: Long? = null,
)

@Entity
class Adjustments(
    var header1: Long,
    var header2: String,
    var header3: String,
    @Id @GeneratedValue var id: Long? = null,
)