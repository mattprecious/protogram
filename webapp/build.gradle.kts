plugins {
  kotlin("js")
  distribution
}

kotlin {
  target {
    browser
  }

  sourceSets["main"].dependencies {
    implementation(project(":protogram"))
    implementation(kotlin("stdlib-js"))
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
