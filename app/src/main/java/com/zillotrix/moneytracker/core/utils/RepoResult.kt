package com.zillotrix.moneytracker.core.utils

sealed interface RepoResult<out D, out E> {
    data class Success<out D>(val data: D) : RepoResult<D, Nothing>
    data class Error<out E>(val error: E) : RepoResult<Nothing, E>
}