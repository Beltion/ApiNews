package com.core.domain.callbacks

interface FailureCallback {
    fun onFailure(tag: String, error: Any?)

}