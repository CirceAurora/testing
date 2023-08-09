import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

apply {
    plugin("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation("net.fabricmc:sponge-mixin:${rootProject.property("mixin_version") as String}")
    compileOnly(kotlin("stdlib"))
}

tasks {
    withType<KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = java.targetCompatibility.majorVersion
    }
}