package samaryanin.avitofork.feature.marketplace.domain.usecase.ad

import samaryanin.avitofork.feature.marketplace.data.repository.ad.AdRepo
import samaryanin.avitofork.feature.marketplace.domain.model.favorites.Ad
import javax.inject.Inject

class GetFilteredAdsUseCase @Inject constructor(
    private val adRepo: AdRepo
) {
    suspend operator fun invoke(categoryIds: List<String>): List<Ad> {
        return adRepo.getFilteredAds(categoryIds)
    }
}