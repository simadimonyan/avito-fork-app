package samaryanin.avitofork.shared.extensions

fun String.formatPrice(): String {
    val number = this.toLongOrNull() ?: return this
    return "%,d ₽".format(number / 100).replace(',', ' ')
}