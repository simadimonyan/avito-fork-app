package samaryanin.avitofork.feature.favorites.domain.usecases

import samaryanin.avitofork.feature.favorites.domain.models.Category
import samaryanin.avitofork.feature.feed.data.repository.AdRepo
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val adRepo: AdRepo
) {
    suspend operator fun invoke(): List<Category> {
        return adRepo.getAllCategories()
    }
}