package com.example.rocketreserver.data.mapper

import com.example.rocketreserver.LaunchListQuery
import com.example.rocketreserver.domain.mapper.Mapper
import com.example.rocketreserver.domain.model.LaunchList
import com.example.rocketreserver.domain.model.LaunchListElement
import javax.inject.Inject

class LaunchListMapper @Inject constructor(
) : Mapper<LaunchListQuery.Launches, LaunchList> {

    override fun map(input: LaunchListQuery.Launches): LaunchList {
        return LaunchList(
            launches = input.launches(),
            cursor = input.cursor,
            hasMore = input.hasMore
        )
    }

    private fun LaunchListQuery.Launches.launches() = launches.filterNotNull().map {
        LaunchListElement(
            id = it.id,
            site = it.site.orEmpty(),
            missionName = it.mission?.name.orEmpty(),
            missionPatch = it.mission?.missionPatch.orEmpty()
        )
    }
}
