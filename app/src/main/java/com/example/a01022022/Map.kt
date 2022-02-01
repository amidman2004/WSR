package com.example.a01022022

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.libraries.maps.MapView
import java.lang.IllegalStateException

@Composable
fun rememberMapView():MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply{
            id = R.id.map_frame
        }
    }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val observer  = mapLifeCycle(mapView = mapView)

    DisposableEffect(lifecycle){
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
    return mapView
}

@Composable
fun mapLifeCycle(mapView: MapView):LifecycleEventObserver =
    remember(mapView) {
        LifecycleEventObserver(){source, event ->
            when(event){
                Lifecycle.Event.ON_CREATE ->mapView.onCreate(Bundle())
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                else -> throw  IllegalStateException()
            }
        }
    }