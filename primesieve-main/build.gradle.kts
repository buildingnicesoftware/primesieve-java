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
    implementation(libs.guava)

    annotationProcessor(libs.lombok)
    testAnnotationProcessor(libs.lombok)
}

application {
    mainClass = "org.math.primesieve.PrimeSieveMain"
    // XXX Normally you wouldn't set multiple targets here, this is just to get things working on both targets
    applicationDefaultJvmArgs = listOf("-Djava.library.path="+project.rootDir+"/primesieve/build/libs/main/macos:"+
            project.rootDir+"/primesieve/build/libs/main/linux")
}
