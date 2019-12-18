plugins {
  `java-library`
  kotlin("jvm")
}

dependencies {
  api(kotlin("stdlib-jdk8"))
  implementation(project(":tinsel", "default"))
  testImplementation("junit", "junit", "4.13-rc-1")
}
