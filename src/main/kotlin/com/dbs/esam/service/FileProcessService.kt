package com.dbs.esam.service

import com.dbs.esam.client.S3Client
import com.dbs.esam.entity.Adjustments
import com.dbs.esam.entity.FileStatus
import com.dbs.esam.repo.AdjustmentsRepo
import com.dbs.esam.repo.FileStatusRepo
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Arrays

@Service
class FileProcessService @Autowired constructor(
    val fileStatusRepo: FileStatusRepo, val s3Client: S3Client, val adjustmentsRepo: AdjustmentsRepo,
) {
    private val log: Logger = LoggerFactory.getLogger(FileProcessService::class.java)

    @Scheduled(fixedRate = 10000)
    fun processFiles() {
        log.info("Scheduler checking for files to be processed")
        fileStatusRepo.findByProcessedFalse()?.forEach {
            log.info("Found the following un-processed file ${it.fileType}/${it.fileName}")
            processFileByType(it)
        }
    }

    @Async
    fun processFileByType(fileStatus: FileStatus) {
        fileStatus.let {
            when (it.fileType) {
                "adjustment" -> {
                    processAdjustmentFile(s3Client.readFile(it.fileType, it.fileName))
                    it.processed = true
                    fileStatusRepo.save(it)
                }
                else -> log.info("Nothing to process")
            }
        }
    }

    @Transactional
    fun processAdjustmentFile(readFile: ByteArray) {
        try {
            val wb: Workbook = XSSFWorkbook(readFile.inputStream())
            val sheet: Sheet = wb.getSheetAt(0)
            sheet.forEach {
                if (it.rowNum > 1 && it.getCell(1).stringCellValue != "") {
                    log.info("${it.getCell(0)}-${it.getCell(1)}-${it.getCell(2)}")
                    adjustmentsRepo.save(
                        Adjustments(
                            it.getCell(0).numericCellValue.toLong(),
                            it.getCell(1).stringCellValue,
                            it.getCell(2).stringCellValue))
                }
            }
        } catch (e: Exception) {
            log.error(e.message)
        }
    }
}