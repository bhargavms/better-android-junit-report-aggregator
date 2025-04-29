package io.github.bhargavms.junit.better.report.aggregator

import java.io.File
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class TestReportAggregationTask : DefaultTask() {
    @get:Input
    abstract val outputDirPath: Property<String>
    @TaskAction
    fun execute() {
        val testReportFiles = project.getAllTestResultXmlFiles()
        mergeXmlFiles(
            rootTagName = "testsuites",
            rootTagParams = mapOf(
                "timestamp" to nowInIso8601,
                "name" to "Aggregated Test"
            ),
            filePaths = testReportFiles,
            outputFile = ensureFile(outputDirPath.get())
        )
    }

    private val nowInIso8601: String get() = let {
        OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME)
    }

    private fun ensureFile(dirPath: String): File {
        val dir = File(dirPath)
        if (dir.exists().not()) {
            dir.mkdirs()
        }
        return File(dir, "aggregated-test-report.xml")
    }
}
