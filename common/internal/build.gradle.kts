dependencies {
    api(project(":common:public", "namedElements"))
    implementation("org.spongepowered:mixin:${rootProject.property("mixin_version") as String}")
}