package com.example.rocketreserver.domain.model

data class LaunchList(
    val launches: List<LaunchListElement>,
    val cursor: String,
    val hasMore: Boolean
)

data class LaunchListElement(
    val id: String,
    val site: String,
    val missionName: String,
    val missionPatch: String
)
