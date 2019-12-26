plugins {
  kotlin("js")
  id("org.jlleitschuh.gradle.ktlint")
  distribution
}

kotlin {
  target {
    browser
  }

  sourceSets["main"].dependencies {
    implementation(project(":protogram"))
    implementation(kotlin("stdlib-js"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.3.3")
  }
}

distributions {
  main {
    contents {
      from("src/main/resources")
      from("$buildDir/distributions/webapp.js")
      into("/")
    }
  }
}
listOf("distZip", "installDist").forEach {
  tasks.named(it).configure {
    dependsOn(tasks.getByName("browserWebpack"))
  }
}
tasks.named("distTar").configure {
  enabled = false
}
