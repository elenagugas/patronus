package com.gugas

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.EnableMBeanExport


@SpringBootApplication
@EnableMBeanExport
open class PatronusService

fun main(args: Array<String>) {
    SpringApplication.run(PatronusService::class.java, *args)
}
