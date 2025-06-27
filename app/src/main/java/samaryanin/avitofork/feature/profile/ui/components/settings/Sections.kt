package samaryanin.avitofork.feature.profile.ui.components.settings

enum class SettingsSection(val title: String?) {
    Search("Регион для поиска"),
    General("Общие"),
    Info("О приложении"),
    Legal("Лицензии и соглашения")
}

enum class SettingsItem(
    val section: SettingsSection,
    val title: String,
    val showDisclosure: Boolean = true,
    val isBlue: Boolean = false
) {
    Region(SettingsSection.Search, "Краснодар"),
    Notifications(SettingsSection.General, "Уведомления"),
    ClearHistory(SettingsSection.General, "Очистить историю поисков", showDisclosure = false),
    Appearance(SettingsSection.General, "Тема, как на телефоне"),
    //Business(SettingsSection.Info, "Решения для бизнеса"),
    Help(SettingsSection.Info, "Помощь"),
    //Rating(SettingsSection.Legal, "Оценить приложение", showDisclosure = false, isBlue = true),
    Rules(SettingsSection.Legal, "Правила AvitoFork"),
    Terms(SettingsSection.Legal, "Оферта на оказание услуг"),
    //Recommendations(SettingsSection.Legal, "Рекомендательные технологии")
}