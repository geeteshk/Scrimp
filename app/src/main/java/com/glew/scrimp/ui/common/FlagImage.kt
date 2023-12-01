package com.glew.scrimp.ui.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import kotlinx.coroutines.delay

@Composable
fun FlagImage(
    modifier: Modifier = Modifier,
    countryCode: String
) {
    AsyncImage(
        model = "https://hatscripts.github.io/circle-flags/flags/${countryCode.lowercase()}.svg",
        contentDescription = null,
        imageLoader = ImageLoader(LocalContext.current)
            .newBuilder()
            .components { add(SvgDecoder.Factory()) }
            .crossfade(true)
            .build(),
        modifier = Modifier.then(modifier)
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FlagImages(
    modifier: Modifier = Modifier,
    countryCodes: List<String>
) {
    var currentIndex by remember {
        mutableStateOf(0)
    }

    Box {
        countryCodes.forEachIndexed { index, countryCode ->
            val alpha by animateFloatAsState(
                targetValue = if (index == currentIndex) 1F else 0F,
                label = "Flag Alpha"
            )

            AsyncImage(
                model = "https://hatscripts.github.io/circle-flags/flags/${countryCode.lowercase()}.svg",
                contentDescription = null,
                imageLoader = ImageLoader(LocalContext.current)
                    .newBuilder()
                    .components { add(SvgDecoder.Factory()) }
                    .build(),
                modifier = Modifier
                    .then(modifier)
                    .alpha(alpha)
            )
        }
    }

    LaunchedEffect(currentIndex) {
        delay(2000)
        var newIndex = currentIndex + 1
        if (newIndex >= countryCodes.size) {
            newIndex = 0
        }

        currentIndex = newIndex
    }
}