plugins {
    java
    application
    jacoco
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.dom4j:dom4j:2.1.3")

    val assertjVersion = "3.16.1"
    val approvalVersion = "9.1.0"

    val junit4Version   = "4.13"
    val junitBomVersion = "5.6.2"

    testImplementation(platform("org.junit:junit-bom:$junitBomVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("junit:junit:${junit4Version}")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine") {
        because("allows JUnit 3 and JUnit 4 tests to run")
    }

    testRuntimeOnly("org.junit.platform:junit-platform-launcher") {
        because("allows tests to run from IDEs that bundle older version of launcher")
    }

    testImplementation("org.assertj:assertj-core:${assertjVersion}")
    testImplementation("com.approvaltests:approvaltests:${approvalVersion}")
    testImplementation("com.google.code.gson:gson:2.8.6"){
        because("Approvals.verifyJson()")
    }
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:all")
    options.compilerArgs.add("--enable-preview")
}

tasks.test {
    jvmArgs("--enable-preview")
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

application {
    mainClass.set("kata.App")
}

jacoco {
    toolVersion = "0.8.5"
}


