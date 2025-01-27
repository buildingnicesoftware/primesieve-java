pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://repo.nokee.dev/release") }
        maven { url = uri("https://repo.nokee.dev/snapshot") }
    }
    val nokeeVersion = "0.5.0-930919a0"
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("dev.nokee.")) {
                useModule("${requested.id.id}:${requested.id.id}.gradle.plugin:${nokeeVersion}")
            }
        }
    }
}

rootProject.name = "primesieve-java"
include("primesieve")
include("primesieve-main")
