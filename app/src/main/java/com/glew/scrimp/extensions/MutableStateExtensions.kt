package com.glew.scrimp.extensions

import androidx.compose.runtime.MutableState

fun <T> MutableState<T>.update(transform: (T) -> T) {
    this.value = transform(this.value)
}