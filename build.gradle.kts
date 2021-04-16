buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.16.0")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt").version("1.16.0")
}

detekt {
    toolVersion = "1.16.0"
    input = files("$projectDir")
    config = files("${project.projectDir}${File.separator}config${File.separator}detekt${File.separator}detekt.yml")
    baseline = file("${project.projectDir}${File.separator}config${File.separator}detekt${File.separator}baseline.xml")
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()

        maven(url = "https://dl.bintray.com/ekito/koin")
    }
}
