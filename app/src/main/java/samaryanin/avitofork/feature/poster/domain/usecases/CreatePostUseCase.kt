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
                MoneyDto(
                    if (state.data.price.contains('.')) {
                        val parts = state.data.price.split('.')
                        val major = parts[0]
                        val minor = (parts.getOrNull(1) ?: "00").padEnd(2, '0').take(2)
                        (major + minor).toLong()
                    } else {
                        (state.data.price + "00").toLong()
                    },
                    CurrencyDto.RUB
                ),
                "test",
                "c66b583c-0d9c-472b-89cc-2dcf4dfde895"
            )
        }
        catch (_: Exception) {
            false
        }
    }

}