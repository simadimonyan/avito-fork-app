package samaryanin.avitofork.feature.poster.domain.usecases

import androidx.compose.runtime.Immutable
import ru.dimagor555.avito.ad.domain.Currency
import ru.dimagor555.avito.ad.domain.Money
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
                Money(
                    if (state.data.price.contains('.')) {
                        val parts = state.data.price.split('.')
                        val major = parts[0]
                        val minor = (parts.getOrNull(1) ?: "00").padEnd(2, '0').take(2)
                        (major + minor).toLong()
                    } else {
                        (state.data.price + "00").toLong()
                    },
                    Currency.RUB
                ),
                "test",
                state.categoryId
            )
        }
        catch (_: Exception) {
            false
        }
    }

}