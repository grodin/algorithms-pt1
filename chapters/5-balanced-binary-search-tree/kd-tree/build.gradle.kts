plugins {
  java
  application
}

application {
//  mainClass.set("Client")
}

val run: JavaExec by tasks
run.standardInput = System.`in`

