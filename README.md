# better-android-junit-report-aggregator: Android JUnit XML Test Report Aggregator

[![Gradle Plugin Portal](https://img.shields.io/gradle-plugin-portal/v/io.github.bhargavms.junit.better-aggregator?label=Gradle%20Plugin%20Portal)](https://plugins.gradle.org/plugin/io.github.bhargavms.junit.better-aggregator)
[![License](https://img.shields.io/github/license/bhargavms/better-android-junit-report-aggregator)](LICENSE)

better-android-junit-report-aggregator elegantly consolidates JUnit XML test results across multi-module, multi-project Gradle architectures in Android projects. It solves the challenge of collecting and accessing distributed test reports in complex build systems without modifying your existing test infrastructure.

## Features

- **Seamless Multi-Module Collection**: Automatically discovers and aggregates test results from all modules
- **Centralized Access**: Creates a single consolidated location for all test results
- **CI/CD Integration**: Simplifies test outcomes for popular CI systems (GitHub Actions, CircleCI, Jenkins)

## Installation

Add to your root build.gradle:

```kotlin
plugins {
    id("io.github.bhargavms.junit.better-aggregator") version "1.0.0"
}
```

Or using the legacy syntax:

```groovy
buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath "io.github.bhargavms.junit.better-aggregator:io.github.bhargavms.junit.better-aggregator.gradle.plugin:1.0.0"
    }
}

apply plugin: "io.github.bhargavms.junit.better-aggregator"
```

## Basic Usage

After applying the plugin, run your tests as normal:

```bash
./gradlew test
```

Then, run the aggregation task:

```bash
./gradlew aggregateJUnitReports
```

This will collect all JUnit XML reports into the `outputDir` directory that you configured.

## Configuration

Configure the plugin in your root build.gradle:

```kotlin
betterAggregator {
    // Output directory (relative to root project)
    outputDir.set("${project.getLayout().buildDirectory.dir("aggregated-reports").get().asFile.absolutePath}")
}
```

### CI Integration

For GitHub Actions:

```yaml
- name: Run tests
  run: ./gradlew test

- name: Aggregate test reports
  run: ./gradlew aggregateJUnitReports

- name: Upload test reports
  uses: actions/upload-artifact@v2
  with:
    name: test-reports
    # If using the outputDir configuration as defined in this README
    path: build/reports/aggregated-junit-reports
```

### Custom Tasks

Create a task that depends on test execution and report aggregation:

```kotlin
tasks.register("testAndAggregate") {
    dependsOn("test", "aggregateJUnitReports")
    group = "Verification"
    description = "Runs tests and aggregates all reports"
}
```

## Compatibility

- Gradle: 7.0+
- Kotlin: 1.5.0+
- Android Gradle Plugin: 4.1.0+
- Java: 8+

## How It Works

The plugin:

1. Scans your project structure to identify modules
2. Searches for JUnit XML reports in configured directories
3. Copies and organizes reports in the output directory
4. Generates a consolidated overview of test results

## Best Practices

- Run the aggregation task after all tests complete
- Include the task in your CI pipeline
- Consider adding the output directory to your .gitignore file

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Ensure you follow [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/)
   Commit your changes (`git commit -m 'feat: Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the Apache 2.0 - see the [LICENSE](LICENSE) file for details.

## Credits

Developed by [Bhargav Srinivasan](https://github.com/bhargavms).

## Similar Projects

- [Kover](https://github.com/Kotlin/kotlinx-kover) - Kotlin coverage tool
- [JaCoCo](https://github.com/jacoco/jacoco) - Java code coverage library
