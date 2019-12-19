import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
}

dependencies {
  implementation(project(":protogram"))
  implementation("com.github.ajalt", "clikt", "2.3.0")

  testImplementation("junit", "junit", "4.13-rc-1")
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}

val fatJar = task("fatJar", type = Jar::class) {
  from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
  with(tasks.jar.get() as CopySpec)

  archiveClassifier.set("fat")

  manifest {
    attributes["Main-Class"] = "com.mattprecious.protogram.Main"
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
