package samaryanin.avitofork.data.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import samaryanin.avitofork.domain.model.post.CategoryField
import java.lang.reflect.Type

class CategoryDeserializer : JsonDeserializer<CategoryField> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): CategoryField {

        val jsonObject = json.asJsonObject

        return when (val type = jsonObject.get("type").asString) {

            "MetaTag" -> CategoryField.MetaTag(
                key = jsonObject.get("key").asString,
                fields = context.deserialize(jsonObject.get("fields"), object : TypeToken<Set<CategoryField>>() {}.type)
            )

            "TextField" -> CategoryField.TextField(
                key = jsonObject.get("key").asString,
                value = jsonObject.get("value").asString
            )

            "NumberField" -> CategoryField.NumberField(
                key = jsonObject.get("key").asString,
                value = jsonObject.get("value").asString,
                unitMeasure = jsonObject.get("unitMeasure").asString
            )

            "DropdownField" -> CategoryField.DropdownField(
                key = jsonObject.get("key").asString,
                value = jsonObject.get("value").asString,
                options = context.deserialize(jsonObject.get("options"), object : TypeToken<Set<String>>() {}.type),
                isOnlyOneToChoose = jsonObject.get("isOnlyOneToChoose").asBoolean
            )

            "PhotoPickerField" -> CategoryField.PhotoPickerField(
                key = jsonObject.get("key").asString,
                count = jsonObject.get("count").asInt
            )

            "PhotoPickerByCategoryField" -> CategoryField.PhotoPickerByCategoryField(
                key = jsonObject.get("key").asString,
                options = context.deserialize(jsonObject.get("options"), object : TypeToken<HashMap<String, Int>>() {}.type)
            )

            "LocationField" -> CategoryField.LocationField(
                key = jsonObject.get("key").asString
            )

            else -> throw JsonParseException("Unknown type CategoryField: $type")

        }

    }

}