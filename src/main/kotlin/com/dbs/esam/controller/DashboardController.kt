package com.dbs.esam.controller

import com.dbs.esam.entity.FileStatus
import com.dbs.esam.repo.FileStatusRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.util.*

@Controller
@RequestMapping("/api")
class DashboardController @Autowired constructor(val repo: FileStatusRepo) {
    val log: Logger = LoggerFactory.getLogger(DashboardController::class.java)

    @GetMapping("/dashboard")
    fun getDashboard(model: Model): String {
        model["list"] = repo.findAll().map { it.render() }
        return "dashboard"
    }
}

private fun FileStatus.render() = RenderFileStatus(
    id,
    fileType,
    fileName,
    uploadedBy,
    uploadedAt.format(),
    if (processed) "Passed" else "Failed"
)

private val englishDateFormatter = DateTimeFormatterBuilder()
    .appendPattern("dd-MM-yyyy")
    .toFormatter(Locale.ENGLISH)

fun LocalDateTime.format(): String = this.format(englishDateFormatter)

data class RenderFileStatus(
    val id: Long?,
    val fileType: String,
    val fileName: String,
    val uploadedBy: String,
    val uploadedAt: String,
    val processed: String,
)

