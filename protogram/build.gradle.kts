plugins {
  kotlin("multiplatform")
  id("org.jlleitschuh.gradle.ktlint") apply false
}

repositories {
  mavenCentral()
}

kotlin {
  jvmToolchain(17)
  
  jvm()
  js {
    browser()
  }

  sourceSets {
    commonMain {
      dependencies {
        api(kotlin("stdlib-common"))
        implementation(project(":tinsel"))
        api("com.squareup.okio:okio:3.7.0")
        api("com.squareup.wire:wire-schema:4.9.7")
      }
    }
    commonTest {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
        implementation(project(":test"))
      }
    }

    jvm().compilations["main"].defaultSourceSet {
      dependencies {
        api(kotlin("stdlib-jdk8"))
      }
    }
    jvm().compilations["test"].defaultSourceSet {
      dependencies {
        implementation(kotlin("test-junit"))
      }
    }
    js().compilations["main"].defaultSourceSet {
      dependencies {
        api(kotlin("stdlib-js"))
      }
    }
    js().compilations["test"].defaultSourceSet {
      dependencies {
        implementation(kotlin("test-js"))
      }
    }
  }
}
