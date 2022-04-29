package com.dbs.esam.controller

import com.dbs.esam.client.S3Client
import com.dbs.esam.entity.FileStatus
import com.dbs.esam.repo.FileStatusRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/file")
class FileProcessController @Autowired constructor(val s3Client: S3Client, val repo: FileStatusRepo) {
    val log: Logger = LoggerFactory.getLogger(FileProcessController::class.java)

    @PostMapping("/upload/{type}")
    fun uploadFile(@PathVariable("type") type: String, @RequestParam("file") file: MultipartFile) {
        //TODO: validation for file type and format
        log.info("Received file [${file.originalFilename}] of type $type")
        s3Client.uploadFile(type, file.originalFilename!!, file)
        repo.save(FileStatus(type, file.originalFilename!!, "User1"))
    }
}