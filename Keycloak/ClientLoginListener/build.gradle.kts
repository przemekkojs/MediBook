plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.medibook"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.keycloak:keycloak-server-spi:24.0.4")
    compileOnly("org.keycloak:keycloak-server-spi-private:24.0.4")
    compileOnly("org.keycloak:keycloak-services:24.0.4")
    implementation("com.rabbitmq:amqp-client:5.14.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

}
tasks {
    shadowJar {
        minimize {
            exclude(dependency("org.keycloak:keycloak-server-spi:24.0.4"))
            exclude(dependency("org.keycloak:keycloak-server-spi-private:24.0.4"))
            exclude(dependency("org.keycloak:keycloak-services:24.0.4"))
        }
        archiveBaseName.set("clientlistener")
        archiveClassifier.set("") // No "-all" suffix
        archiveVersion.set("1.0.0")

        manifest {
            attributes["Main-Class"] = "com.example.MainKt" // replace with your main class
        }
    }
}



tasks.test {
    useJUnitPlatform()
}