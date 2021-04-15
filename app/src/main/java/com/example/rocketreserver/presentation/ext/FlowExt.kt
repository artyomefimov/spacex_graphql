package com.example.rocketreserver.presentation.ext

import com.example.rocketreserver.presentation.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

suspend inline fun <T> Flow<Event<T>>.collectEvent(crossinline handleAction: (T) -> Unit) =
    collect {
        it.getContent()?.let(handleAction)
    }
