import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  java
  kotlin("jvm") version "1.3.61"
}

group = "com.mattprecious"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation("com.squareup.okio", "okio", "2.4.2")
  implementation("com.squareup.wire", "wire-runtime", "3.0.2")

  testCompile("junit", "junit", "4.13-rc-1")
}

configure<JavaPluginConvention> {
  sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}

val fatJar = task("fatJar", type = Jar::class) {
  from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
  with(tasks.jar.get() as CopySpec)

  archiveClassifier.set("fat")

  manifest {
    attributes["Main-Class"] = "com.mattprecious.protogram.Protogram"
  }
}

val binaryJar = task("binaryJar") {
  val binaryDir = File(buildDir, "bin")
  val binaryFile = File(binaryDir, "protogram")

  doLast {
    val fatJarFile = fatJar.archiveFile.get().asFile

    binaryFile.parentFile.mkdirs()
    binaryFile.appendText("#!/bin/sh\n\nexec java -jar \$0 \"\$@\"\n\n")
    binaryFile.appendBytes(fatJarFile.readBytes())

    binaryFile.setExecutable(true)
  }
}

tasks {
  "assemble" {
    dependsOn(binaryJar)
  }

  binaryJar.dependsOn(fatJar)
}