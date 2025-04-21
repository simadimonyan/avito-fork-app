package samaryanin.avitofork.feature.marketplace.domain.usecase.ad

import samaryanin.avitofork.feature.marketplace.data.repository.ad.AdRepo
import javax.inject.Inject

class GetImageBytesByIdUseCase @Inject constructor(
    private val adRepo: AdRepo
) {
    suspend operator fun invoke(imageId: String): ByteArray {
      return  adRepo.getImageBytesById(imageId)
    }
}