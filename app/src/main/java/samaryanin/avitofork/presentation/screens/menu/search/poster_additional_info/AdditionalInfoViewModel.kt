package samaryanin.avitofork.presentation.screens.menu.search.poster_additional_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import samaryanin.avitofork.data.network.repository.AdRepo
import samaryanin.avitofork.domain.model.Ad
import samaryanin.avitofork.presentation.screens.menu.search.navigation.NavigationHolder

@HiltViewModel
class AdditionalInfoViewModel
@javax.inject.Inject constructor
    (
    private val adRepo: AdRepo,
) : ViewModel() {

    private val _adById = MutableStateFlow<Ad?>(null)
    val adById : MutableStateFlow<Ad?> get() = _adById

    init {
        getAdById(NavigationHolder.id?: "1")

    }

    fun getAdById(adId: String) {
        viewModelScope.launch {
            _adById.value = adRepo.getAdById(adId)
        }
    }
}
//в обжекте хранить лист айди выбранных
//запрос по листу айди
//добавить коил