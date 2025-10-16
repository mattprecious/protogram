import org.jetbrains.kotlin.gradle.plugin.kotlinToolingVersion

plugins {
  base
  kotlin("multiplatform") version "2.2.20" apply false
  kotlin("jvm") version "2.2.20" apply false
  kotlin("js") version "2.2.20" apply false
  id("org.jlleitschuh.gradle.ktlint") version "12.1.0" apply false
}

subprojects {
  group = "com.mattprecious"
  version = "0.3.0-SNAPSHOT"

  repositories {
    mavenCentral()
  }
}
