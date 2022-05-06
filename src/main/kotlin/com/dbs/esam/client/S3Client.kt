package com.dbs.esam.client

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Service
class S3Client {
    val log: Logger = LoggerFactory.getLogger(S3Client::class.java)

    init {
        //TODO: Need to setup S3 client with url & credentials
    }

    fun uploadFile(type: String, fileName: String, file: MultipartFile): Boolean {
        //TODO: Upload file to S3 location
        log.info("Uploading file $type/$fileName to S3")
        val folder = File(type)
        if (!folder.exists()) {
            log.info("Folder does not exist. Creating it")
            folder.mkdir()
        }
        val uploadedFile = File(folder, fileName)
        uploadedFile.writeBytes(file.inputStream.readAllBytes())
        log.info("Uploading done")
        return true
    }

    fun readFile(type: String, fileName: String): ByteArray {
        //TODO: Read file from S3 location
        log.info("Reading file $type/$fileName from S3")
        val folder = File(type)
        return File(folder, fileName).readBytes()
    }
}