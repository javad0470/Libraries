package com.javad0470.map


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.MapView
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun GoogleMap() {
    val mapView = rememberMapViewWithLifecycle()
    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { mapView }
        ) { mapView ->
            CoroutineScope(Dispatchers.Main).launch {
                val map = mapView.awaitMap()
                map.uiSettings.apply {
                    isZoomControlsEnabled = true
                    isMyLocationButtonEnabled = true
                }

                val location1 = Location("")
                val myLocation = Location("")

                map.setOnMapClickListener {
                    Toast.makeText(context,
                        location1.distanceTo(myLocation).toString(),
                        Toast.LENGTH_SHORT).show()
                    location1.latitude = it.latitude
                    location1.longitude = it.longitude
                }

                map.setOnMyLocationClickListener {
                    myLocation.longitude = it.longitude
                    myLocation.latitude = it.latitude
                }

                checkPermission(context = context, map = map)
            }
        }
    }
}

fun checkPermission(
    context: Context,
    map: GoogleMap,
) {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED
    ) {
       // map.isMyLocationEnabled = true
    }
}


@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = com.google.maps.android.ktx.R.id.map_frame
        }
    }
    val lifeCycleObserver = rememberMapLifecycleObserver(mapView = mapView)
    val lifeCycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(key1 = lifeCycle) {
        lifeCycle.addObserver(lifeCycleObserver)
        onDispose {
            lifeCycle.removeObserver(lifeCycleObserver)
        }
    }
    return mapView
}

@Composable
fun rememberMapLifecycleObserver(
    mapView: MapView,
): LifecycleEventObserver = remember(mapView) {
    LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
            Lifecycle.Event.ON_START -> mapView.onStart()
            Lifecycle.Event.ON_RESUME -> mapView.onResume()
            Lifecycle.Event.ON_PAUSE -> mapView.onPause()
            Lifecycle.Event.ON_STOP -> mapView.onStop()
            Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
            else -> throw IllegalStateException()
        }
    }
}


