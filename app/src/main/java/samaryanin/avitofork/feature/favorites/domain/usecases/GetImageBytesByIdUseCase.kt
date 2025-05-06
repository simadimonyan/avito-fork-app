package samaryanin.avitofork.feature.favorites.domain.usecases

import samaryanin.avitofork.feature.feed.data.repository.AdRepo
import javax.inject.Inject

class GetImageBytesByIdUseCase @Inject constructor(
    private val adRepo: AdRepo
) {
    suspend operator fun invoke(imageId: String): ByteArray {
      return  adRepo.getImageBytesById(imageId)
    }
}