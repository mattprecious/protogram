import org.jetbrains.kotlin.gradle.plugin.KotlinCompilationToRunnableFiles

plugins {
  kotlin("multiplatform")
}

kotlin {
  jvm()
  js()

  sourceSets {
    commonMain {
      dependencies {
        implementation(kotlin("stdlib-common"))
        implementation(project(":tinsel"))
        implementation("com.squareup.okio:okio-multiplatform:2.4.2")
        implementation("com.squareup.wire:wire-runtime-multiplatform:3.0.2")
      }
    }
    commonTest {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
        implementation(project(":test"))
      }
    }

    jvm().compilations["main"].defaultSourceSet {
      dependencies {
        implementation(kotlin("stdlib-jdk8"))
      }
    }
    jvm().compilations["test"].defaultSourceSet {
      dependencies {
        implementation(kotlin("test-junit"))
      }
    }
    js().compilations["main"].defaultSourceSet {
      dependencies {
        implementation(kotlin("stdlib-js"))
      }
    }
    js().compilations["test"].defaultSourceSet {
      dependencies {
        implementation(kotlin("test-js"))
      }
    }
  }
}

val fatJar = task("fatJar", type = Jar::class) {
  val mainCompilation = kotlin.targets["jvm"].compilations["main"] as KotlinCompilationToRunnableFiles
  from(mainCompilation.output)
  afterEvaluate {
    from(mainCompilation.runtimeDependencyFiles.map { if (it.isDirectory) it else zipTree(it) })
  }

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
