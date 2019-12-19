plugins {
  kotlin("multiplatform")
}

kotlin {
  jvm()
  js()

  sourceSets {
    commonMain {
      dependencies {
        implementation(kotlin("stdlib-common"))
        implementation(project(":tinsel"))
      }
    }
    commonTest {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
      }
    }
    jvm().compilations["main"].defaultSourceSet {
      dependencies {
        implementation(kotlin("stdlib-jdk8"))
      }
    }
    jvm().compilations["test"].defaultSourceSet {
      dependencies {
        implementation(kotlin("test-junit"))
      }
    }
    js().compilations["main"].defaultSourceSet {
      dependencies {
        implementation(kotlin("stdlib-js"))
      }
    }
    js().compilations["test"].defaultSourceSet {
      dependencies {
        implementation(kotlin("test-js"))
      }
    }
  }
}
