package com.mahmoud.cloudmessaging.core

object CloudMessageBuildType {
    enum class BuildType {
        DEBUG, RELEASE
    }

    private var buildType: BuildType = BuildType.RELEASE

    fun setBuildType(buildType: BuildType) {
        CloudMessageBuildType.buildType = buildType
    }

    fun isDeBug() = buildType == BuildType.DEBUG

    fun isRelease() = buildType == BuildType.RELEASE
}
