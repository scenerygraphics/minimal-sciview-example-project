import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.23"
    application
    `maven-publish`
}

group = "graphics.scenery"
version = "0.3.0"

repositories {
    mavenCentral()
    maven("https://maven.scijava.org/content/groups/public")
    maven("https://jitpack.io")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "graphics.scenery"
            artifactId = "minimal-sciview-example-project"
            version = "0.3.0"

            from(components["java"])
        }
    }
}

dependencies {
    api("sc.iview:sciview:0.3.0") {
        exclude("org.jogamp.jogl","jogl-all")
        exclude("org.jogamp.gluegen","gluegen-rt")
    }

    api("graphics.scenery:scenery:0.10.1") {
        exclude("org.jogamp.jogl","jogl-all")
        exclude("org.jogamp.gluegen","gluegen-rt")
    }

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "21"
}

tasks.withType<JavaExec> {
    if(javaVersion >= JavaVersion.VERSION_17) {
        allJvmArgs = allJvmArgs + listOf("--add-opens=java.base/java.nio=ALL-UNNAMED",
                                         "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED")
    }
}

application {
    mainClass.set("sc.iview.minimal.MyDemo")
}
