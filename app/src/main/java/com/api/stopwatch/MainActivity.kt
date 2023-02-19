package com.api.stopwatch

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.api.stopwatch.ui.theme.StopWatchTheme
import com.api.stopwatch.ui.theme.back

import kotlinx.coroutines.delay
import java.time.format.TextStyle
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StopWatchTheme {
                MyApp()
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyApp() {
    Scaffold(
        backgroundColor = back,
        topBar = { TopBar() }
    ) {
        Text(
            text = "يوم بدينا",
            fontFamily = FontFamily(Font(R.font.tajawal)),
            fontSize = 55.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black.copy(0.8f),
            modifier = Modifier.padding(top = 64.dp, end = 100.dp)

        )
        textExpand()

        RotatingImage()



        Image(
            painter = painterResource(id = R.drawable.pe),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 590.dp, end = 95.dp)
                .size(550.dp)

        )

        Image(
            painter = painterResource(id = R.drawable.hor1),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 8.dp)
                .size(150.dp)
                .rotate(90f)
                .absoluteOffset(y = 25.dp)

        )


    }
}

@Composable
fun TopBar() {
    TopAppBar(

    ) {
        Image(
            painter = painterResource(id = R.drawable.day),
            contentDescription = null,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun textExpand() {
    val text =
        "نعتزّ بذكرى تأسيس هذه الدولة المباركة في العام 1139هـ (1727م)، ومنذ ذلك التاريخ وحتى اليوم؛ أرست ركائز السلم والاستقرار وتحقيق العدل والحمد لله على كل النعم الملك سلمان بن عبد العزيز\n" +
                "\n"
    val MINIMIZED_MAX_LINES = 2
    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var isClickable by remember { mutableStateOf(false) }
    var finalText by remember { mutableStateOf(text) }

    val textLayoutResult = textLayoutResultState.value
    LaunchedEffect(textLayoutResult) {
        if (textLayoutResult == null) return@LaunchedEffect

        when {
            isExpanded -> {
                finalText = "$text إخفاء النص "
            }
            !isExpanded && textLayoutResult.hasVisualOverflow -> {
                val lastCharIndex = textLayoutResult.getLineEnd(MINIMIZED_MAX_LINES - 1)
                val showMoreString = "... أظهر النص"
                val adjustedText = text
                    .substring(startIndex = 0, endIndex = lastCharIndex)
                    .dropLast(showMoreString.length)
                    .dropLastWhile { it == ' ' || it == '.' }

                finalText = "$adjustedText$showMoreString"

                isClickable = true
            }
        }
    }

    Text(
        text = finalText,
        fontWeight = FontWeight.Medium,
        maxLines = if (isExpanded) Int.MAX_VALUE else MINIMIZED_MAX_LINES,
        onTextLayout = { textLayoutResultState.value = it },
        modifier = Modifier
            .padding(top = 148.dp, end = 100.dp)
            .clickable(enabled = isClickable) { isExpanded = !isExpanded }
            .animateContentSize()
            .padding(20.dp)
    )
}

@Composable
fun RotatingImage() {
    var degrees by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(30) // adjust this value to change the speed of rotation
            degrees += 1f
        }
    }
    AnimatedVisibility(
        visible = true, // change this to control the visibility of the image
        enter = fadeIn() + expandIn(),
        exit = shrinkOut()
    ) {
        Image(
            painter = painterResource(id = R.drawable.hor),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 380.dp, end = 60.dp)
                .rotate(degrees)
                .size(500.dp)
                .alpha(0.6f)
        )
    }
}


