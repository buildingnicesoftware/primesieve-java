plugins {
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // This dependency is used by the application.
    implementation(project(":primesieve"))
    implementation(libs.slf4j.log4j12)
    implementation(libs.log4j.core)
    implementation(libs.log4j.api)
    implementation(libs.log4j.slf4j.api)
    implementation(libs.lombok)

    annotationProcessor(libs.lombok)
    testAnnotationProcessor(libs.lombok)
}

application {
    mainClass = "org.math.primesieve.PrimeSieveMain"
    applicationDefaultJvmArgs = listOf("-Djava.library.path="+project.rootDir+"/primesieve/build/libs/main/")
}
