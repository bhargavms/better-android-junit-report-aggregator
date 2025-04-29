package io.github.bhargavms.junit.better.report.aggregator.extensions

import org.gradle.api.provider.Property

interface TestReportAggregatorExtension {
    val outputDir: Property<String>
}
