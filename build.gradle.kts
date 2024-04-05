import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml



plugins {
  `java-library`
  id("io.papermc.paperweight.userdev") version "1.5.13"
  id("xyz.jpenilla.run-paper") version "2.2.3"
  id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.1.1"
  id("maven-publish")
}

group = "de.derioo.chals.server"
version = "0.0.0"
description = "Backend Server Mod Wrapper"

java {
  toolchain.languageVersion = JavaLanguageVersion.of(17)
}

dependencies {
  paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")

  compileOnly("org.projectlombok:lombok:1.18.30")
  annotationProcessor("org.projectlombok:lombok:1.18.30")
  testCompileOnly("org.projectlombok:lombok:1.18.30")
  testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks {
  assemble {
    dependsOn(reobfJar)
  }

  compileJava {
    options.encoding = Charsets.UTF_8.name()

    options.release = 17
  }
  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }

}



publishing {
  repositories {
    maven {
      name = "GitHubPackages"
      url = uri("https://maven.pkg.github.com/Knerio/Simple-Chals-Server")
      credentials {
        username = project.properties["GITHUB_USERNAME"].toString()
        password = project.properties["GITHUB_TOKEN"].toString()
      }
    }
  }
  publications {
    register<MavenPublication>("gpr") {
      groupId = "de.derioo.chals"
      artifactId = "api"
      version = "0.0.7"
      from(components["java"])
    }
  }
}

bukkitPluginYaml {
  main = "de.derioo.chals.server.ServerCore"
  load = BukkitPluginYaml.PluginLoadOrder.STARTUP
  authors.add("Dario")
  apiVersion = "1.20"
}
