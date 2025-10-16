plugins {
  kotlin("multiplatform")
  id("org.jlleitschuh.gradle.ktlint") apply false
  distribution
}

kotlin {
  js {
    browser {
      webpackTask {
        mainOutputFileName = "webapp.js"
      }
    }
    binaries.executable()
  }

  sourceSets {
    val jsMain by getting {
      dependencies {
        implementation(project(":protogram"))
        implementation(kotlin("stdlib-js"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.7.3")
      }
    }
  }
}

distributions {
  main {
    contents {
      from("src/main/resources")
      from("${layout.buildDirectory.get()}/distributions/webapp.js")
      into("/")
    }
  }
}

listOf("distZip", "installDist").forEach {
  tasks.named(it).configure {
    dependsOn(tasks.named("jsBrowserProductionWebpack"))
  }
}

tasks.named("distTar").configure {
  enabled = false
}
