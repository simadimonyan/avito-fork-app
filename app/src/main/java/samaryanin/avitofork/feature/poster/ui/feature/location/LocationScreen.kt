package samaryanin.avitofork.feature.poster.ui.feature.location

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.VisibleRegionUtils
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.Address
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.SearchType
import com.yandex.mapkit.search.Session
import com.yandex.mapkit.search.ToponymObjectMetadata
import com.yandex.runtime.Error
import ru.dimagor555.avito.category.domain.field.FieldData
import samaryanin.avitofork.R
import samaryanin.avitofork.feature.poster.domain.models.Location
import samaryanin.avitofork.feature.poster.domain.models.PostData
import samaryanin.avitofork.feature.poster.domain.models.PostState
import samaryanin.avitofork.feature.poster.ui.feature.location.components.LocationSearchBar
import samaryanin.avitofork.feature.poster.ui.feature.location.components.Pin
import samaryanin.avitofork.feature.poster.ui.state.CategoryEvent
import samaryanin.avitofork.feature.poster.ui.state.CategoryViewModel
import samaryanin.avitofork.shared.ui.components.utils.space.Space
import samaryanin.avitofork.shared.ui.theme.lightBlue

@Preview
@Composable
fun LocationPreview() {
    val gap: (String, String, Double, Double) -> Unit = {_, _, _, _ -> }
    LocationContent(MapView(LocalContext.current), false, {}, Point(), gap, {})
}

@Composable
fun LocationScreen(globalNavController: NavHostController, categoriesViewModel: CategoryViewModel) {

    val state by categoriesViewModel.categoryStateHolder.categoryState.collectAsState()

    var dragging by remember { mutableStateOf(false) }
    var getMyLocation by remember { mutableStateOf(false) }
    var currentCameraPosition by remember { mutableStateOf(Point()) }

    val toggleLocation: () -> Unit = {
        getMyLocation = !getMyLocation
        dragging = true
    }

    val setLocation: (String, String, Double, Double) -> Unit = { location, region, longitude, latitude ->
        val draft = state.tempDraft
        categoriesViewModel.handleEvent(CategoryEvent.UpdateDraftParams(
            PostState(
                draft.categoryName,
                draft.categoryId,
                PostData(
                    draft.data.name,
                    draft.data.photos,
                    draft.data.price,
                    draft.data.unit,
                    draft.data.description,
                    draft.data.options,
                    FieldData.AddressValue(location, region, longitude, latitude)
                ),
                draft.timestamp
            )
        ))
    }

    val onExit: () -> Unit = {
        globalNavController.navigateUp()
    }

    val context = LocalContext.current
    MapKitFactory.initialize(context)
    MapKitFactory.getInstance().onStart()
    val mapView = remember { MapView(context) }

    val fusedClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
    }

    val cameraListener =
        CameraListener { _, position, cameraUpdateReason, finished ->
            if ((cameraUpdateReason == CameraUpdateReason.GESTURES
                        || cameraUpdateReason == CameraUpdateReason.APPLICATION) && !finished) {
                dragging = true
            }
            else if (finished) {
                currentCameraPosition = position.target
                dragging = false
            }
        }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    LaunchedEffect(hasPermission, getMyLocation) {
        if (hasPermission) {
            try {
                val token = CancellationTokenSource().token
                val task = fusedClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    token
                )
                task.addOnSuccessListener { location ->
                    if (location != null) {
                        Log.d("LocationScreen", "Location received: ${location.latitude}, ${location.longitude}")
                        val point = Point(location.latitude, location.longitude)

                        mapView.mapWindow.map.move(
                            CameraPosition(point, 19f, 0f, 50f),
                            Animation(Animation.Type.LINEAR, 1f),
                            null
                        )
                    } else {
                        Log.e("LocationScreen", "Location is null")
                    }
                }
                task.addOnFailureListener { e ->
                    Log.e("LocationScreen", "Location error: ${e.localizedMessage}")
                }
            } catch (e: Exception) {
                Log.e("LocationScreen", "Location error: ${e.localizedMessage}")
            }
        }
    }

    // Attach the tap listener to the map
    mapView.mapWindow.map.addCameraListener(cameraListener)

    DisposableEffect(Unit) {
        onDispose {
            MapKitFactory.getInstance().onStop()
        }
    }

    LocationContent(mapView, dragging, toggleLocation, currentCameraPosition, setLocation, onExit)
}

@Composable
fun LocationContent(
    mapView: MapView,
    dragging: Boolean,
    toggleLocation: () -> Unit,
    currentCameraPosition: Point,
    setLocation: (String, String, Double, Double) -> Unit,
    onExit: () -> Unit
) {

    var search by remember { mutableStateOf("") }
    var searchFocus by remember { mutableStateOf(false) }
    var searchLoading by remember { mutableStateOf(false) }

    var address by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf(0.0) }
    var longitude by remember { mutableStateOf(0.0) }
    var region by remember { mutableStateOf("") }

    Scaffold(containerColor = Color.LightGray, floatingActionButton = {
        if (!searchFocus) {
            FloatingActionButton(
                onClick = {
                    toggleLocation()
                },
                containerColor = Color.White,
                modifier = Modifier.padding(bottom = 70.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.send_message),
                    contentDescription = "myLocation",
                    tint = lightBlue,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }) { innerPadding ->

        val searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)
        val searchOptions = SearchOptions().apply {
            searchTypes = SearchType.BIZ.value
            resultPageSize = 32
        }

        val state = remember { mutableStateListOf(Location(search, "", null)) }

        val searchSessionListener = object : Session.SearchListener {

            override fun onSearchResponse(response: Response) {
                searchLoading = false
                val items = response.collection.children.mapNotNull { it.obj }
                Log.d("Search", "Found ${items.size} items")
                items.forEach {
                    if (it.name != null && it.descriptionText != null) {
                        val point = it.geometry.firstOrNull()?.point
                        state.add(Location(it.name!!, it.descriptionText!!, point))
                    }
                    Log.d("Search", "• ${it.name}")
                }
            }

            override fun onSearchError(error: Error) {
                searchLoading = false
                Log.d("MapsSearchError", error.toString())
            }
        }

        Box(modifier = Modifier.padding(innerPadding), contentAlignment = Alignment.BottomCenter) {

            val focusManager = LocalFocusManager.current

            AndroidView(factory = { mapView }, modifier = Modifier.fillMaxSize())

            Column {

                Card(
                    modifier = if (searchFocus) Modifier.fillMaxSize()
                        else Modifier.wrapContentSize(),
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column {
                        LocationSearchBar(onExit, search, {
                            // поиск по карте
                            search = it
                            state.clear()
                            searchLoading = true
                            searchManager.submit(
                                search,
                                VisibleRegionUtils.toPolygon(mapView.mapWindow.map.visibleRegion),
                                searchOptions,
                                searchSessionListener,
                            )
                        }) { focusState ->
                            searchFocus = focusState.hasFocus
                        }

                        if (searchFocus) {

                            searchLoading = true
                            searchManager.submit(
                                search,
                                VisibleRegionUtils.toPolygon(mapView.mapWindow.map.visibleRegion),
                                searchOptions,
                                searchSessionListener,
                            )

                            Spacer(modifier = Modifier.background(Color.LightGray).height(3.dp))

                            LazyColumn {

                                if (state.isEmpty() && !searchLoading) {
                                    item {
                                        Column(Modifier.fillMaxWidth().padding(top = 100.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text("Ничего не найдено", fontWeight = FontWeight.Bold)
                                            Space(2.dp)
                                            Text("Попробуйте изменить запрос.", color = Color.LightGray)
                                        }
                                    }
                                }
                                else {
                                    items(state.size) { item ->
                                        val location = state[item]
                                        Row(modifier = Modifier.fillMaxWidth().padding(5.dp).clickable {

                                            focusManager.clearFocus()
                                            state.clear()

                                            if (location.point != null) {
                                                mapView.mapWindow.map.move(
                                                    CameraPosition(location.point, 19f, 0f, 50f),
                                                    Animation(Animation.Type.LINEAR, 1f),
                                                    null
                                                )
                                            }

                                        }, verticalAlignment = Alignment.Top) {
                                            Space()
                                            Card(
                                                modifier = Modifier,
                                                colors = CardDefaults.cardColors(containerColor = lightBlue),
                                                elevation = CardDefaults.elevatedCardElevation(2.dp),
                                                shape = RoundedCornerShape(10.dp)
                                            ) {
                                                Icon(
                                                    painter = painterResource(R.drawable.search),
                                                    contentDescription = "pin",
                                                    tint = Color.White,
                                                    modifier = Modifier
                                                        .size(30.dp).padding(10.dp)
                                                )
                                            }
                                            Space()
                                            Column(modifier = Modifier.wrapContentSize()) {
                                                Text(location.name, fontWeight = FontWeight.Bold)
                                                Space(2.dp)
                                                Text(location.description)
                                            }
                                        }
                                    }

                                }
                            }

                        }
                        else
                            state.clear()

                        if (!searchFocus) {

                            val listener = object : Session.SearchListener {
                                override fun onSearchResponse(response: Response) {
                                    val toponym = response.collection.children
                                        .mapNotNull { it.obj }
                                        .firstOrNull { it.metadataContainer.getItem(
                                            ToponymObjectMetadata::class.java) != null }

                                    toponym?.let {
                                        val geoObj = it.metadataContainer
                                            .getItem(ToponymObjectMetadata::class.java)

                                        address = geoObj.address.formattedAddress
                                        latitude = geoObj.balloonPoint.latitude
                                        longitude = geoObj.balloonPoint.longitude
                                        region = geoObj?.address?.components?.firstOrNull {
                                            comp -> comp.kinds.contains(Address.Component.Kind.REGION)
                                                || comp.kinds.contains(Address.Component.Kind.AREA)
                                                || comp.kinds.contains(Address.Component.Kind.DISTRICT)
                                                || comp.kinds.contains(Address.Component.Kind.LOCALITY)
                                                || comp.kinds.contains(Address.Component.Kind.PROVINCE)
                                                || comp.kinds.contains(Address.Component.Kind.OTHER)
                                                || comp.kinds.contains(Address.Component.Kind.UNKNOWN)
                                        }?.name.toString()

                                        Log.d("ReverseGeocode", "Address: $address")
                                    }
                                }

                                override fun onSearchError(error: Error) {
                                    Log.e("ReverseGeocode", "Error: $error")
                                }
                            }

                            searchManager.submit(
                                currentCameraPosition,
                                mapView.mapWindow.map.cameraPosition.zoom.toInt(),
                                SearchOptions(),
                                listener
                            )

                            if (currentCameraPosition.latitude != 0.0 && currentCameraPosition.longitude != 0.0) {
                                Row(Modifier.fillMaxWidth().padding(10.dp)) {
                                    Text(address)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Card(
                    modifier = Modifier,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Button(
                        onClick = {
                            Log.d("Location", "Setting: $address, $region, $latitude, $longitude")
                            setLocation(address, region, latitude, longitude)
                            onExit()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth().padding(10.dp)
                    ) {
                        Text("Указать местоположение", fontSize = 15.sp)
                    }
                }
            }

            if (!searchFocus) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Pin(dragging = dragging)
                }
            }
        }
    }
}