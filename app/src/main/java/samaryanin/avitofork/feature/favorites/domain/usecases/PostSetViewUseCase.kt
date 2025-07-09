package samaryanin.avitofork.feature.favorites.domain.usecases

import io.ktor.client.statement.HttpResponse
import samaryanin.avitofork.feature.feed.data.repository.AdRepo
import javax.inject.Inject

class PostSetViewUseCase @Inject constructor(
    private val adRepo: AdRepo
) {
    suspend operator fun invoke(adId: String): HttpResponse {
        return adRepo.setView(adId)
    }
}