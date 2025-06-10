package samaryanin.avitofork.feature.poster.ui.shared.text

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class PriceVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {

        val separator = '.'
        var outputText = ""

        var cleanInput = text.text
        // Удаляем все нецифровые символы, кроме первого десятичного разделителя
        val builder = StringBuilder()
        var decimalAdded = false
        for (char in cleanInput) {
            if (char.isDigit()) {
                builder.append(char)
            } else if (char == separator && !decimalAdded) {
                builder.append(separator)
                decimalAdded = true
            }
        }
        cleanInput = builder.toString()

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var originalIndex = 0
                var formattedIndex = 0

                while (originalIndex < offset && formattedIndex < outputText.length) {
                    if (outputText[formattedIndex].isDigit() || outputText[formattedIndex] == separator) {
                        originalIndex++
                    }
                    formattedIndex++
                }
                return formattedIndex
            }

            override fun transformedToOriginal(offset: Int): Int {
                var originalOffset = 0
                var formattedIndex = 0

                while (formattedIndex < offset && formattedIndex < outputText.length) {
                    if (outputText[formattedIndex].isDigit() || outputText[formattedIndex] == separator) {
                        originalOffset++
                    }
                    formattedIndex++
                }
                return originalOffset
            }
        }

        if (cleanInput.isNotEmpty()) {
            val symbols = DecimalFormatSymbols.getInstance(Locale.US).apply {
                groupingSeparator = ' ' // разделитель тысяч
                decimalSeparator = separator // десятичный разделитель
            }

            val formatter = DecimalFormat("#,##0.##", symbols).apply {
                decimalFormatSymbols = symbols
            }

            if (cleanInput == "." || cleanInput.endsWith(".")) {
                val intPart = cleanInput.dropLast(1).toLongOrNull() ?: 0L
                outputText = formatter.format(intPart) + separator

            } else if (cleanInput.contains(separator)) {

                val index = cleanInput.indexOfFirst { it == separator }
                if (index >= 0 && index < cleanInput.length - 1) {
                    val whole = cleanInput.substring(0, index)
                    val frac = cleanInput.substring(index)
                    val intPart = whole.toLongOrNull() ?: 0L
                    outputText = formatter.format(intPart.toDouble()) + frac
                } else {
                    val number = cleanInput.toDoubleOrNull() ?: 0.0
                    outputText = formatter.format(number)
                }

            } else {
                val number = cleanInput.toDoubleOrNull() ?: 0.0
                outputText = formatter.format(number)
            }
        }

        return TransformedText(
            text = AnnotatedString(outputText),
            offsetMapping = numberOffsetTranslator
        )
    }
}
