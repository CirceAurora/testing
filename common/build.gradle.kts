subprojects {
    dependencies {
        implementation("net.fabricmc:sponge-mixin:${rootProject.property("mixin_version") as String}")
    }
}