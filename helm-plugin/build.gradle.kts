plugins {
    kotlin("jvm")
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish")
    id("org.jetbrains.dokka")
    id("maven-publish")
    alias(libs.plugins.detekt)
    alias(libs.plugins.binaryCompatibilityValidator)
}


dependencies {

    implementation(libs.snakeyaml)
    implementation(libs.orgJson)

    implementation(libs.unbrokenDomePluginUtils)

    testImplementation(libs.jsonPath)
    testImplementation(libs.jacksonDataBind)
    testImplementation(libs.jacksonDataFormatYaml)

    testImplementation(libs.okHttpMockWebServer)

    testImplementation(libs.unbrokenDomeTestUtils)
    testImplementation(libs.bundles.defaultTests)
    testRuntimeOnly(libs.junitEngine)
}


gradlePlugin {
    plugins {
        create("helmCommandsPlugin") {
            id = "com.citi.helm-commands"
            displayName = "Helm Commands plugin"
            implementationClass = "com.citi.gradle.plugins.helm.command.HelmCommandsPlugin"
            description = "Wrapper for common helm commands"
        }
        create("helmPlugin") {
            id = "com.citi.helm"
            displayName = "Helm plugin"
            implementationClass = "com.citi.gradle.plugins.helm.HelmPlugin"
            description = "Gradle plugin to help preparing Helm Charts. Supports charts packaging, linting, dependencies update, etc."
        }
    }
}

apiValidation {
    ignoredPackages.add("com.citi.gradle.plugins.helm.dsl.internal")
}