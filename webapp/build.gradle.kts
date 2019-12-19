plugins {
  kotlin("js")
}

dependencies {
  implementation(project(":protogram"))
  implementation(kotlin("stdlib-js"))
}

kotlin {
  target {
    browser
  }
}
