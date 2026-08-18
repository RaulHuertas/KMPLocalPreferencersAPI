package com.rhuertas.kmplocalpreferencesapi

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.visible
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import kotlinx.coroutines.launch

import kmplocalpreferencesapi.shared.generated.resources.Res
import kmplocalpreferencesapi.shared.generated.resources.compose_multiplatform

@Composable
@Preview
fun App(
    optionsStore: OptionsStore? = null,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "progress_bar_transition")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "progress_value"
    )

    val resolvedStore = optionsStore ?: remember { InMemoryOptionsStore() }
    val coroutineScope = rememberCoroutineScope()
    val options by resolvedStore.options.collectAsState(initial = null)
    var didNotifyOptionsLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(options, didNotifyOptionsLoaded) {
        val loadedOptions = options ?: return@LaunchedEffect
        if (!didNotifyOptionsLoaded) {
            didNotifyOptionsLoaded = true
        }
    }

    val currentOptions = options ?: DefaultOptions

    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            Text(
                "Saved options: color=${currentOptions.color}, mode=${currentOptions.mode}, dark=${currentOptions.dark_mode}",
                modifier = Modifier.visible(didNotifyOptionsLoaded)
            )
            Button(
                enabled = options != null,
                onClick = {
                    options?.let { loadedOptions ->
                        coroutineScope.launch {
                            resolvedStore.saveOptions(
                                loadedOptions.copy(
                                    color = if (loadedOptions.color == "blue") "green" else "blue",
                                    mode = loadedOptions.mode + 1,
                                    dark_mode = !loadedOptions.dark_mode
                                )
                            )
                        }
                    }
                },
                modifier = Modifier.visible(didNotifyOptionsLoaded)
            ) {
                Text("Save options")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .visible(!didNotifyOptionsLoaded)
                ,
                contentAlignment = Alignment.BottomCenter
            ) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth(1.0f)
                )
                Text("Loading options...")
            }

        }
    }
}