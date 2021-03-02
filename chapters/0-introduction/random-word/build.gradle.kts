plugins {
  application
}
application {
  mainClass.set("RandomWord")
}
val run: JavaExec by tasks
run.standardInput = System.`in`
