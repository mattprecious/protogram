plugins {
  kotlin("jvm")
  id("org.jlleitschuh.gradle.ktlint")
}

kotlin {
  jvmToolchain(17)
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(project(":protogram"))
  implementation("com.github.ajalt", "clikt", "2.3.0")

  testImplementation("junit", "junit", "4.13")
  testImplementation("com.google.jimfs", "jimfs", "1.1")
}

tasks {
  val fatJar = register<Jar>("fatJar", {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(jar.get() as CopySpec)

    archiveClassifier.set("fat")

    manifest {
      attributes["Main-Class"] = "com.mattprecious.protogram.Main"
    }
  })

  val binaryJar = register("binaryJar", {
    val binaryDir = layout.buildDirectory.dir("bin").get().asFile
    val binaryFile = binaryDir.resolve("protogram")

    doLast {
      val fatJarFile = fatJar.get().archiveFile.get().asFile

      binaryFile.parentFile.mkdirs()
      binaryFile.writeText("#!/bin/sh\n\nexec java -jar \$0 \"\$@\"\n\n")
      binaryFile.appendBytes(fatJarFile.readBytes())

      binaryFile.setExecutable(true)
    }
  })

  named("assemble") {
    dependsOn(binaryJar)
  }

  binaryJar {
    dependsOn(fatJar)
  }
}
