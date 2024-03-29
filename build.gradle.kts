plugins {
  base
  kotlin("multiplatform") version "1.6.21" apply false
  kotlin("jvm") version "1.6.21" apply false
  kotlin("js") version "1.6.21" apply false
  id("org.jlleitschuh.gradle.ktlint") version "9.1.1" apply false
}

subprojects {
  group = "com.mattprecious"
  version = "0.3.0-SNAPSHOT"

  repositories {
    mavenCentral()
  }
}
