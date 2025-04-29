package io.github.bhargavms.junit.better.report.aggregator

import io.github.bhargavms.junit.better.report.aggregator.extensions.TestReportAggregatorExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register

class TestReportAggregator : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions
            .create<TestReportAggregatorExtension>("betterAggregator")
        val outputFile = extension.outputDir
        target.tasks.register<TestReportAggregationTask>("aggregateJUnitXMLReports") {
            outputDirPath.set(outputFile)
        }
    }
}
