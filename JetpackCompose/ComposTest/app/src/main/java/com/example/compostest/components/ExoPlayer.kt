package com.example.compostest.components

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.compostest.R
import com.example.compostest.ui.theme.Purple500
import com.example.compostest.utils.Constance.MUSIC_URL
import com.example.compostest.utils.Constance.VIDEO_URL
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerControlView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

@SuppressLint("RememberReturnType")
@Composable
fun ExoPlayer() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Purple500)
        ) {
            Text(
                text = "ExoPlayer Video",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
        val exoPlayer = remember(context) {
            SimpleExoPlayer.Builder(context).build().apply {
                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                    context, Util.getUserAgent(context, context.packageName)
                )

                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(VIDEO_URL))

                this.prepare(source)
            }
        }

        AndroidView(factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
            }
        })
    }
}

@Composable
fun VideoPlayer() {
    val context = LocalContext.current
    // Create exoPlayer instance
    val player = SimpleExoPlayer.Builder(context).build()
    // Create PlayerView
    val playerView = PlayerView(context)
    // Build Media Item
    val mediaItem = MediaItem.fromUri(VIDEO_URL)
    val playWhenReady by remember {
        mutableStateOf(true)
    }

    player.setMediaItem(mediaItem)
    playerView.player = player

    LaunchedEffect(key1 = player) {
        player.prepare()
        player.playWhenReady = playWhenReady
    }

    AndroidView(factory = {
        playerView
    })
}

fun musicPlayer(
    context: Context
) {
    val player = SimpleExoPlayer.Builder(context).build()
    val playerView = PlayerView(context)
    val mediaItem = MediaItem.fromUri(MUSIC_URL)

    playerView.player = player
    player.setMediaItem(mediaItem)
    player.prepare()
    player.play()
}

@Composable
fun MusicPlayerUi() {
    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = { musicPlayer(context = context) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_play_item),
                    contentDescription = "Play Icon",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun MusicPreview() {
    MusicPlayerUi()
}










