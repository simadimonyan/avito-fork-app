package samaryanin.avitofork.feature.feed.domain.models.feed

import kotlinx.serialization.Serializable

@Serializable
data class SearchRequest(val query: String)