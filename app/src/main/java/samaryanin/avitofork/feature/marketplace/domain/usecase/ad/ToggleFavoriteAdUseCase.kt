package samaryanin.avitofork.feature.marketplace.domain.usecase.ad

import samaryanin.avitofork.feature.marketplace.data.repository.ad.AdRepo
import javax.inject.Inject

class ToggleFavoriteAdUseCase @Inject constructor(
    private val adRepo: AdRepo
) {
    suspend operator fun invoke(adId: String, isFavorite: Boolean) {
        adRepo.toggleFavouriteAd(adId, isFavorite)
    }
}