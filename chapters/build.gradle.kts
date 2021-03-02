plugins {
    java
    idea
}


subprojects {
  apply {
    plugin("org.gradle.java")

  }
  repositories {
    mavenCentral()
    maven("https://dl.bintray.com/algs4/maven/")
  }
  dependencies {
    implementation("edu.princeton.cs:algs4:1.0.4")
  }

}
