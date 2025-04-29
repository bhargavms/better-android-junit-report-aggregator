buildscript {
    dependencies {
        classpath(libs.plugin.android.gradle)
        classpath(libs.plugin.kotlin.gradle)
    }
}

plugins {
    alias(libs.plugins.kotlin.lint.jlleitschuh)
}

tasks.ktlintFormat {
    dependsOn(
        subprojects
            .filterNot(::isPlainDir)
            .map { "${it.path}:ktlintFormat" }
    )
}

tasks.register("check") {
    dependsOn(subprojects.map { "${it.name}:check" })
}

fun isPlainDir(project: Project) =
    !project.file("build.gradle").exists() && !project.file("build.gradle.kts").exists()
