import dev.nokee.platform.nativebase.SharedLibraryBinary

plugins {
    id("dev.nokee.jni-library")
    id("dev.nokee.cpp-language")
    id("java")
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit test framework.
    testImplementation(libs.junit)

    // This dependency is used by the application.
    implementation(libs.slf4j)
    implementation(libs.slf4j.log4j12)
    implementation(libs.log4j.core)
    implementation(libs.log4j.api)
    implementation(libs.log4j.slf4j.api)
    implementation(libs.lombok)
    implementation(libs.guava)

    annotationProcessor(libs.lombok)
    testAnnotationProcessor(libs.lombok)
}

tasks.withType<Test> {
    // For 1B comparison test
    jvmArgs = listOf("-Xmx2g")
}

library {
    // Only Intel MacOS is developed and tested
    targetMachines.set(listOf(machines.macOS.x86_64))

    binaries.configureEach {
        if (this is SharedLibraryBinary) {
            compileTasks.configureEach {
                if (this is AbstractNativeCompileTask) {
                    // Required for noexcept on OSX x86_64
                    (this as AbstractNativeCompileTask).compilerArgs.add("-std=c++11");
                }
            }
            linkTask.configure {
                linkerArgs.add("-L/usr/local/lib/")
                linkerArgs.add("-lprimesieve")
                // Debugging purposes
                linkerArgs.add("-v")
            }
        }
    }
}
