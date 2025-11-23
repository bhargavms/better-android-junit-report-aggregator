package io.github.bhargavms.junit.better.report.aggregator

import java.io.File
import kotlin.collections.orEmpty
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
    val includedBuildFiles = gradle.includedBuilds.flatMap { includedBuild ->
        findTestResultXmlFiles(includedBuild.projectDir)
    }
    subprojectFiles + includedBuildFiles
}

private fun findTestResultXmlFiles(rootDir: File): List<String> {
    val results = mutableListOf<String>()
    // Scan root and potential subproject directories
    val projectDirs = listOf(rootDir) + (
        rootDir.listFiles()?.filter {
            it.isDirectory && (
                File(it, "build.gradle").exists() ||
                    File(it, "build.gradle.kts").exists()
                )
        } ?: emptyList()
        )

    projectDirs.forEach { projectDir ->
        val testResultsDir = File(projectDir, "build/test-results")
        if (testResultsDir.exists() && testResultsDir.isDirectory) {
            testResultsDir.walk()
                .filter { it.isFile && it.name.startsWith("TEST-") && it.name.endsWith(".xml") }
                .forEach { results.add(it.absolutePath) }
        }
    }

    return results
}
