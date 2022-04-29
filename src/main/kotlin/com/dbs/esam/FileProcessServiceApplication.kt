package com.dbs.esam

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableTransactionManagement
class FileProcessServiceApplication

fun main(args: Array<String>) {
    runApplication<FileProcessServiceApplication>(*args)
}
