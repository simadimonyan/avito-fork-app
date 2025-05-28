package samaryanin.avitofork.feature.poster.domain.usecases

import androidx.compose.runtime.Immutable
import ru.dimagor555.avito.ad.dto.CurrencyDto
import ru.dimagor555.avito.ad.dto.MoneyDto
import samaryanin.avitofork.feature.poster.data.repository.PosterRepository
import samaryanin.avitofork.feature.poster.domain.models.PostState
import javax.inject.Inject
import javax.inject.Singleton

@Immutable
@Singleton
class CreatePostUseCase @Inject constructor(
    private val posterRepository: PosterRepository
) {

    suspend fun create(state: PostState): Boolean {
        return try {
            posterRepository.create(
                state.data.name,
                state.data.description,
                state.data.photos.values.toList(),
                MoneyDto(state.data.price.replace(".", "").toLong(), CurrencyDto.RUB),
                "test",
                "14e61896-d5fe-4555-9134-032ec26e101e"
            )
        }
        catch (_: Exception) {
            false
        }
    }

}