pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Cocktails"
include(":app")
include(":data:api")
include(":domain:search")
include(":domain:random")
include(":domain:details")
include(":presentation:search")
include(":presentation:details")
include(":presentation:random")
include(":domain:model")
include(":presentation:model")
include(":presentation:core")
include(":data:cache")
include(":domain:sharedCache")
