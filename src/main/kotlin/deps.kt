import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.*

val fullComposeLibs = listOf(
    "androidx.compose.runtime:runtime-dispatch:$ANDROID_COMPOSE_VERSION",
    "androidx.compose.runtime:runtime:$ANDROID_COMPOSE_VERSION",
    "androidx.compose.animation:animation-core:$ANDROID_COMPOSE_VERSION",
    "androidx.compose.animation:animation:$ANDROID_COMPOSE_VERSION",
    "androidx.compose.ui:ui:$ANDROID_COMPOSE_VERSION",
    "androidx.compose.foundation:foundation:$ANDROID_COMPOSE_VERSION",
    "androidx.compose.ui:ui-geometry:$ANDROID_COMPOSE_VERSION",
    "androidx.compose.ui:ui-graphics:$ANDROID_COMPOSE_VERSION",
    "androidx.compose.foundation:foundation-layout:$ANDROID_COMPOSE_VERSION",
    "androidx.compose.runtime:runtime-livedata:$ANDROID_COMPOSE_VERSION",
    "androidx.compose.material:material:$ANDROID_COMPOSE_VERSION",
    "androidx.compose.material:material-icons-core:$ANDROID_COMPOSE_VERSION",
    "androidx.compose.material:material-icons-extended:$ANDROID_COMPOSE_VERSION",
    "androidx.compose.runtime:runtime-rxjava2:$ANDROID_COMPOSE_VERSION",
    "androidx.compose.ui:ui-text:$ANDROID_COMPOSE_VERSION",
    "androidx.compose.ui:ui-text-android:$ANDROID_COMPOSE_VERSION",
    "androidx.compose.ui:ui-util:$ANDROID_COMPOSE_VERSION",
    "androidx.ui:ui-tooling:$ANDROID_COMPOSE_VERSION"
)

val reactLibs = listOf(
    "org.jetbrains.kotlinx:kotlinx-html:0.7.2",
    "org.jetbrains:kotlin-react:$REACT_VERSION-$KOTLIN_WRAPPER_VERSION",
    "org.jetbrains:kotlin-react-dom:$REACT_VERSION-$KOTLIN_WRAPPER_VERSION",
    "org.jetbrains:kotlin-styled:1.0.0-$KOTLIN_WRAPPER_VERSION",
    "org.jetbrains:kotlin-extensions:1.0.1-$KOTLIN_WRAPPER_VERSION",
    "org.jetbrains:kotlin-css-js:1.0.0-$KOTLIN_WRAPPER_VERSION"
)

inline fun KotlinDependencyHandler.implementationCompose() {
    fullComposeLibs.forEach {
        implementation(it)
    }
}

private fun DependencyHandler.`implementation`(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

fun DependencyHandler.implementationCompose() {
    fullComposeLibs.forEach {
        implementation(it)
    }
}

inline fun KotlinDependencyHandler.implementationComposeApi() = implementationCompose()

inline fun Project.fixComposeWithWorkaround() {
    if (System.getProperty("buildJetPackCompose") == "true") {
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            //JetPack Workaround here (more info at https://github.com/avdim/compose_mpp_workaround)
            //https://mvnrepository.com/artifact/androidx.compose/compose-compiler/1.0.0-alpha01
            val composeCompilerJar =
                rootProject.file("compose-compiler-1.0.0-alpha03.jar").absolutePath //need download jar
            kotlinOptions.freeCompilerArgs += listOf("-Xuse-ir", "-Xplugin=$composeCompilerJar")
        }
    }
}

fun DependencyHandler.implementationReact() {
    reactLibs.forEach {
        implementation(it)
    }
}

inline fun KotlinDependencyHandler.implementationReact() {
    reactLibs.forEach {
        implementation(it)
    }
}

inline fun KotlinDependencyHandler.apiReact() {
    reactLibs.forEach {
        api(it)
    }
}

inline fun org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet.addJvmSourceDirs() {
    kotlin.srcDir("src/telegramMain/kotlin")
    kotlin.srcDir("src/ideaMain/kotlin")
    kotlin.srcDir("src/consoleMain/kotlin")
}
