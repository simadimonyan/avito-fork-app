package samaryanin.avitofork.feature.poster.domain.usecases

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Immutable
import ru.dimagor555.avito.image.dto.UploadImageTypeDto
import samaryanin.avitofork.feature.poster.data.repository.ImageRepository
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Immutable
class UploadAdImageUseCase @Inject constructor(
    val imageRepository: ImageRepository
) {

    suspend fun upload(context: Context, uri: Uri): String {
        val file = getFileFromUri(context, uri)
        if (file != null) {
            return imageRepository.upload(file, UploadImageTypeDto.AD).id
        }
        return "";
    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val fileName = "android_ad_image_${System.currentTimeMillis()}.png"
        val tempFile = File(context.cacheDir, fileName)
        tempFile.outputStream().use { output ->
            inputStream.copyTo(output)
        }
        return tempFile
    }

}