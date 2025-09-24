package com.simbiri.domain.util

// wrapper interface for handling errors and success http responses when requesting data of our model classes
// from the database
sealed interface ResultType <out D, out E: Error> {
    // success output value D can be of any datatype, a list, unit, etc
    data class Success<out D>(val data: D) : ResultType<D, Nothing>
    data class Failure<out E: Error>(val error: E) : ResultType<Nothing, E>
}

inline fun <T, E: Error>ResultType<T, E>.onSuccess(action: (T) -> Unit): ResultType<T, E> {
    if (this is ResultType.Success) action(data)
    return this
}

inline fun <T, E: Error>ResultType<T, E>.onFailure(action: (E) -> Unit): ResultType<T, E> {
    if (this is ResultType.Failure) action(error)
    return this
}
