package samaryanin.avitofork.feature.marketplace.domain.usecase.ad

import samaryanin.avitofork.feature.marketplace.data.repository.ad.AdRepo
import samaryanin.avitofork.feature.marketplace.domain.model.favorites.Category
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val adRepo: AdRepo
) {
    suspend operator fun invoke(): List<Category> {
        return adRepo.getAllCategories()
    }
}