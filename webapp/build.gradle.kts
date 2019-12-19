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
tasks.named("distZip").configure {
  dependsOn(tasks.getByName("browserWebpack"))
}
tasks.named("distTar").configure {
  enabled = false
}
