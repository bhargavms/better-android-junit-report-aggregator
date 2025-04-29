@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

rootProject.name = "better-android-junit-report-aggregator"

dependencyResolutionManagement {
    repositories {
        gradlePluginPortal() // org.gradle.android.cache-fix:org.gradle.android.cache-fix.gradle.plugin
        mavenCentral()
        google()
    }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

include("plugin-test-report-aggregator")
