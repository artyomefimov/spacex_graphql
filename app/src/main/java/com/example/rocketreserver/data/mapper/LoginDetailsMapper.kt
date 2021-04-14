package com.example.rocketreserver.data.mapper

import com.example.rocketreserver.LoginMutation
import com.example.rocketreserver.domain.mapper.Mapper
import com.example.rocketreserver.domain.model.LoginDetails

class LoginDetailsMapper: Mapper<LoginMutation.Data, LoginDetails> {

    override fun map(input: LoginMutation.Data): LoginDetails {
        return LoginDetails(
            login = input.login.orEmpty()
        )
    }
}