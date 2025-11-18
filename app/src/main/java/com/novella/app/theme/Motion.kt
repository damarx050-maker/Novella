package com.novella.app.theme

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween

object Motion {
    // Durations
    const val EnterExit = 200
    const val Pressed = 120

    // Easing
    val DefaultEasing = FastOutSlowInEasing

    // Common tweens
    val EnterExitTween = tween<Int>(durationMillis = EnterExit, easing = DefaultEasing)
    val PressedTween = tween<Int>(durationMillis = Pressed, easing = DefaultEasing)
}
