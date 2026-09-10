plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven {
        name = "EngineHub Repository"
        url = uri("https://maven.enginehub.org/repo/")
    }
}

dependencies {
    implementation(gradleApi())
    implementation(libs.shadow)
    implementation(libs.paperweight)

    constraints {
        val asmVersion = "[${libs.versions.minimumAsm.get()},)"
        implementation("org.ow2.asm:asm:$asmVersion") {
            because("Need Java 21 support in shadow")
        }
        implementation("org.ow2.asm:asm-commons:$asmVersion") {
            because("Need Java 21 support in shadow")
        }
        implementation("org.vafer:jdependency:[${libs.versions.minimumJdependency.get()},)") {
            because("Need Java 21 support in shadow")
        }
    }
}

fun ensurePluginSpecBuildersDirs() {
    val metaDir = layout.buildDirectory.dir("kotlin-dsl/precompiled-script-plugins-metadata/plugin-spec-builders").get().asFile
    metaDir.mkdirs()
    val importsFile = File(metaDir, "kotlinDslPluginSpecBuildersImplicitImports")
    if (!importsFile.exists()) {
        importsFile.createNewFile()
    }
    val genDir = layout.buildDirectory.dir("generated-sources/kotlin-dsl-external-plugin-spec-builders/kotlin/gradle/kotlin/dsl/plugins/_b63ff5cd45ef163ef5c5b6362a8c1562").get().asFile
    genDir.mkdirs()
    val specFile = File(genDir, "PluginSpecBuilders.kt")
    if (!specFile.exists()) {
        specFile.writeText("package gradle.kotlin.dsl.plugins._b63ff5cd45ef163ef5c5b6362a8c1562\n")
    }
}

ensurePluginSpecBuildersDirs()

tasks.named("extractPrecompiledScriptPluginPlugins") {
    doLast {
        ensurePluginSpecBuildersDirs()
    }
}

tasks.named("generateExternalPluginSpecBuilders") {
    mustRunAfter("extractPrecompiledScriptPluginPlugins")
    outputs.cacheIf { false }
    outputs.upToDateWhen { false }
    doLast {
        ensurePluginSpecBuildersDirs()
    }
}

gradle.taskGraph.beforeTask {
    if (name.startsWith("compilePluginsBlocks")) {
        ensurePluginSpecBuildersDirs()
    }
}


