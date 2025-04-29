package io.github.bhargavms.junit.better.report.aggregator

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

private val xmlHeader =
    """<\?xml\s+version\s*=\s*['"]1.0['"]\s+encoding\s*=\s*['"]UTF-8['"]\s*\?>""".toRegex()

internal fun mergeXmlFiles(
    rootTagName: String,
    rootTagParams: Map<String, String>,
    filePaths: List<String>,
    outputFile: File
) {
    // Flag to track if the first line has been processed
    var isFirstLine = true
    val rootTagParamValues = rootTagParams.map {
        """
        ${it.key}="${it.value}"
        """.trimIndent()
    }.toList().joinToString(" ")
    // Use BufferedWriter for efficient writing
    BufferedWriter(FileWriter(outputFile)).use { writer ->
        // Loop through each file path
        for (filePath in filePaths) {
            // Open a reader for the current file
            File(filePath).bufferedReader().use { reader ->
                // Loop through each line in the file
                reader.lineSequence().forEach { line ->
                    // Omit the <?xml version="1.0" encoding="UTF-8"?> declaration
                    if (isFirstLine || !line.contains(xmlHeader)) {
                        // Write the line to the output file
                        writer.write(line)
                        writer.newLine()
                    }
                    // Update the flag after processing the first line
                    if (isFirstLine) {
                        isFirstLine = false
                        // attach root tag after first line
                        writer.write("<$rootTagName $rootTagParamValues>")
                        writer.newLine()
                    }
                }
            }
        }
        writer.write("</$rootTagName>")
        writer.newLine()
    }
}
