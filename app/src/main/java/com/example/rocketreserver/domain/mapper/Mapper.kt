package com.example.rocketreserver.domain.mapper

interface Mapper<IN, OUT> {
    fun map(input: IN): OUT
}
