package samaryanin.avitofork.feature.poster.ui.shared.text

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat
import java.text.NumberFormat

class PriceVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {

        val symbols = DecimalFormat().decimalFormatSymbols
        val decimalSeparator = symbols.decimalSeparator

        var outputText = ""
        var integerPart = 0L
        var decimalPart = ""

        var cleanInput = text.text
        // Remove all non-digit characters except the first decimal separator
        val builder = StringBuilder()
        var decimalAdded = false
        for (char in cleanInput) {
            if (char.isDigit()) {
                builder.append(char)
            } else if (char == decimalSeparator && !decimalAdded) {
                builder.append(char)
                decimalAdded = true
            }
        }
        cleanInput = builder.toString()

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var transformedOffset = 0
                var originalIndex = 0
                var formattedIndex = 0

                while (originalIndex < offset && formattedIndex < outputText.length) {
                    if (outputText[formattedIndex].isDigit() || outputText[formattedIndex] == decimalSeparator) {
                        originalIndex++
                    }
                    formattedIndex++
                    transformedOffset = formattedIndex
                }
                return transformedOffset
            }

            override fun transformedToOriginal(offset: Int): Int {
                var originalOffset = 0
                var formattedIndex = 0

                while (formattedIndex < offset && formattedIndex < outputText.length) {
                    if (outputText[formattedIndex].isDigit() || outputText[formattedIndex] == decimalSeparator) {
                        originalOffset++
                    }
                    formattedIndex++
                }
                return originalOffset
            }
        }

        if (cleanInput.isNotEmpty()) {
            val number = cleanInput.toDoubleOrNull() ?: 0.0
            integerPart = number.toLong()
            outputText += NumberFormat.getIntegerInstance().format(integerPart)
            if (cleanInput.contains(decimalSeparator)) {
                decimalPart = cleanInput.substring(cleanInput.indexOf(decimalSeparator))
                if (decimalPart.isNotEmpty()) {
                    outputText += decimalPart
                }
            }
        }

        return TransformedText(
            text = AnnotatedString(outputText),
            offsetMapping = numberOffsetTranslator
        )

    }

}