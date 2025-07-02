package samaryanin.avitofork.feature.favorites.domain.usecases

import samaryanin.avitofork.feature.favorites.domain.models.Ad
import samaryanin.avitofork.feature.feed.data.repository.AdRepo
import javax.inject.Inject

class GetUsersAdsUseCase @Inject constructor(
    private val adRepo: AdRepo
) {
    suspend operator fun invoke(): List<Ad> {
        return adRepo.getUsersAd()
    }
}