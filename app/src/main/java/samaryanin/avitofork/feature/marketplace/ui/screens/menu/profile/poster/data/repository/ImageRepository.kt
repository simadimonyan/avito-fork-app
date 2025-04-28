package samaryanin.avitofork.feature.marketplace.ui.screens.menu.profile.poster.data.repository

import androidx.compose.runtime.Stable
import samaryanin.avitofork.core.network.KtorClient
import javax.inject.Inject
import javax.inject.Singleton

@Stable
@Singleton
class ImageRepository @Inject constructor(
    ktorClient: KtorClient
) {

    private val httpClient = ktorClient.httpClient
    private val strictUrl: String = "/api/v1"



}