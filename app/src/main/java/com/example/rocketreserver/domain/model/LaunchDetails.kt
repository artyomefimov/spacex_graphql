package com.example.rocketreserver.domain.model

data class LaunchDetails(
    val id: String,
    val site: String,
    val missionName: String,
    val missionPatch: String,
    val isBooked: Boolean,
    val rocket: Rocket
)