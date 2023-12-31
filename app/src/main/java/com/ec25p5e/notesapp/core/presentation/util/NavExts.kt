package com.ec25p5e.notesapp.core.presentation.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.Days
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Composable
fun LottieView(
    @RawRes json: Int,
    iterations: Int = Int.MAX_VALUE,
    modifier: Modifier = Modifier
){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(json))
    LottieAnimation(
        composition,
        iterations = iterations,
        modifier = modifier,
    )
}

val dateFormat = "dd-MM-yyyy"

val today: String
    get(){
        val c: Date = Calendar.getInstance().getTime()
        println("Current time => $c")

        val df = SimpleDateFormat(dateFormat, Locale.getDefault())
        return df.format(c)
    }

val String.format12Hour: String
    get() {
        try {
            val parts = this.split(":")
            if(parts.size==2){
                var a = "AM"
                var h = parts[0].toInt()
                val m = parts[1].toInt()
                if(h>12){
                    h -= 12
                    a = "PM"
                }
                return String.format("%02d:%02d %s",h,m,a)
            }
        } catch (e: Exception) {
        }
        return ""
    }

fun daysBetween(left: String, right: String): Int?{
    try {
        val parts = left.split("-")
        val parts1 = right.split("-")
        return -Days
            .daysBetween(
                DateTime(
                    parts[2].toInt(),
                    parts[1].toInt(),
                    parts[0].toInt(),
                    0,
                    0,
                    DateTimeZone.getDefault()
                ),
                DateTime(
                    parts1[2].toInt(),
                    parts1[1].toInt(),
                    parts1[0].toInt(),
                    0,
                    0,
                    DateTimeZone.getDefault()
                )
            ).days
    } catch (e: Exception) {
        return null
    }
}

val LocalDate.formatted: String
    get() {
        val y = this.year
        val m = this.monthValue
        val d = this.dayOfMonth

        return String.format("%02d-%02d-%04d", d, m, y)
    }

val LocalTime.formatted: String
    get() {
        val h = this.hour
        val m = this.minute
        return String.format("%02d:%02d",h,m)
    }

val String.time: String
    get() {
        return "\\d{2}:\\d{2}".toRegex().find(this)?.groupValues?.getOrNull(0)?:""
    }

val String.date: String
    get() {
        return "\\d{2}-\\d{2}-\\d{4}".toRegex().find(this)?.groupValues?.getOrNull(0)?:""
    }
