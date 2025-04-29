package io.github.bhargavms.junit.better.report.aggregator

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test

internal fun Project.getAllTestResultXmlFiles(): List<String> = let {
    val subprojectFiles = subprojects.flatMap { subproject ->
        val testTask = subproject.tasks.withType(Test::class.java)
        val files = testTask.flatMap {
            it.reports.junitXml.outputLocation.asFile.orNull?.let { file ->
                if (file.isDirectory) {
                    file.listFiles { _, name -> name.endsWith(".xml") }?.toList()
                } else {
                    listOf(file)
                }
            }.orEmpty()
        }.filterNotNull().filter {
            it.exists() && it.name.endsWith(".xml")
        }
        files.map {
            it.absolutePath
        }
    }
    subprojectFiles
}
