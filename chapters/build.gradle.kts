plugins {
  java
  idea
  id("com.adarshr.test-logger") version "2.1.1"
}


subprojects {
  apply {
    plugin("org.gradle.java")
    plugin("com.adarshr.test-logger")
  }
  repositories {
    mavenCentral()
    maven("https://dl.bintray.com/algs4/maven/")
  }
  dependencies {
    implementation("edu.princeton.cs:algs4:1.0.4")
    testImplementation(project(":common"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("com.google.truth:truth:1.1.2")
    testImplementation("com.google.truth.extensions:truth-java8-extension:1.1.2")
  }
  tasks.test {
    useJUnitPlatform()
  }

}

