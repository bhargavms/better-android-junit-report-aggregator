plugins {
    `kotlin-dsl`
    alias(libs.plugins.kotlin.lint.jlleitschuh)
    id("com.gradle.plugin-publish") version "1.3.1"
}
group = "io.github.bhargavms"
version = "1.0.0"

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    version.set("0.43.2")
    android.set(true)
    ignoreFailures.set(false)
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

val Provider<PluginDependency>.dependency
    get() = map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }

gradlePlugin {
    website.set("https://github.com/bhargavms/better-android-junit-report-aggregator")
    vcsUrl.set("https://github.com/bhargavms/better-android-junit-report-aggregator")
    plugins {
        create("junitXMLReportAggregator") {
            id = "io.github.bhargavms.junit.better-aggregator"
            implementationClass =
                "io.github.bhargavms.junit.better.report.aggregator.TestReportAggregator"
            displayName = "Better Android JUnit Report Aggregator"
            description =
                "Elegantly Consolidate Existing Test Results Across Multi-Module, " +
                "Multi-Project Gradle Architectures"
            tags.set(
                listOf(
                    "junit",
                    "testing",
                    "report",
                    "aggregation",
                    "android",
                    "multi module report",
                    "xml",
                    "ci",
                    "test results",
                    "report aggregation"
                )
            )
        }
    }
}

dependencies {
    implementation(libs.plugin.android.gradle)
    implementation(libs.plugin.kotlin.gradle)
    implementation(libs.plugins.android.junit5.support.dependency)
    implementation(libs.plugins.kotlin.lint.jlleitschuh.dependency)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
