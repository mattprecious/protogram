plugins {
  kotlin("js")
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
