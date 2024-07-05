plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.junit:junit-bom:5.10.0"))
    implementation("org.junit.jupiter:junit-jupiter")
    implementation("io.rest-assured:rest-assured:5.4.0")
    implementation("io.rest-assured:json-schema-validator:5.4.0")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.junit.jupiter:junit-jupiter-params:5.10.3")
    implementation("com.jayway.jsonpath:json-path:2.9.0")
    implementation("org.assertj:assertj-core:3.26.0")
    implementation("net.javacrumbs.json-unit:json-unit-assertj:3.4.0")
    implementation("com.github.javafaker:javafaker:1.0.2")




}

tasks.test {
    useJUnitPlatform()
}