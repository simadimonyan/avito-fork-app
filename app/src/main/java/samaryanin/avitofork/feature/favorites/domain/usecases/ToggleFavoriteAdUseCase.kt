package samaryanin.avitofork.feature.favorites.domain.usecases

import samaryanin.avitofork.feature.feed.data.repository.AdRepo
import javax.inject.Inject

class ToggleFavoriteAdUseCase @Inject constructor(
    private val adRepo: AdRepo
) {
    suspend operator fun invoke(adId: String, isFavorite: Boolean) {
        adRepo.toggleFavouriteAd(adId, isFavorite)
    }
}