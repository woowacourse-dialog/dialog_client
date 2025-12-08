package com.on.convention

import com.on.convention.extension.androidExtensions
import com.on.convention.extension.isAndroidProject
import com.on.convention.extension.library
import com.on.convention.extension.libs
import com.on.convention.extension.testImplementation
import org.gradle.api.Project
import org.gradle.api.tasks.testing.AbstractTestTask
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestDescriptor
import org.gradle.api.tasks.testing.TestResult
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.KotlinClosure2
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

internal class KotestConventionPlugin :
    BuildLogicPlugin({
        useTestPlatformForTarget()

        dependencies {
            testImplementation(libs.library("kotest-framework"))
        }
    })

private fun Project.useTestPlatformForTarget() {
    fun AbstractTestTask.setTestConfiguration() {
        outputs.upToDateWhen { false }
        testLogging {
            events =
                setOf(
                    TestLogEvent.PASSED,
                    TestLogEvent.SKIPPED,
                    TestLogEvent.FAILED,
                )
        }
        afterSuite(
            KotlinClosure2<TestDescriptor, TestResult, Unit>({ desc, result ->
                if (desc.parent == null) {
                    val output =
                        "Results: ${result.resultType} " +
                            "(${result.testCount} tests, " +
                            "${result.successfulTestCount} passed, " +
                            "${result.failedTestCount} failed, " +
                            "${result.skippedTestCount} skipped)"
                    println(output)
                }
            }),
        )
    }

    if (isAndroidProject) {
        androidExtensions.testOptions {
            unitTests.all { test ->
                test.useJUnitPlatform()

                if (!test.name.contains("debug", ignoreCase = true)) {
                    test.enabled = false
                }
            }
        }
        tasks.withType<Test>().configureEach {
            setTestConfiguration()
        }
    } else {
        tasks.withType<Test>().configureEach {
            useJUnitPlatform()
            setTestConfiguration()
        }
    }
}
