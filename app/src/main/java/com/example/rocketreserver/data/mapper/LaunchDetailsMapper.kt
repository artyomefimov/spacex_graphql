package com.example.rocketreserver.data.mapper

import com.example.rocketreserver.LaunchDetailsQuery
import com.example.rocketreserver.domain.mapper.Mapper
import com.example.rocketreserver.domain.model.LaunchDetails
import com.example.rocketreserver.domain.model.Rocket

class LaunchDetailsMapper : Mapper<LaunchDetailsQuery.Launch, LaunchDetails> {

    override fun map(input: LaunchDetailsQuery.Launch): LaunchDetails {
        return LaunchDetails(
            id = input.id,
            site = input.site.orEmpty(),
            missionName = input.mission?.name.orEmpty(),
            missionPatch = input.mission?.missionPatch.orEmpty(),
            isBooked = input.isBooked,
            rocket = Rocket(
                name = input.rocket?.name.orEmpty(),
                type = input.rocket?.type.orEmpty()
            )
        )
    }
}
