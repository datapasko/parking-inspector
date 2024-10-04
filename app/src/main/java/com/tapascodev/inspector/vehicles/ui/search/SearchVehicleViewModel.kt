package com.tapascodev.inspector.vehicles.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.vehicles.domain.model.Vehicle
import com.tapascodev.inspector.vehicles.domain.usecases.GetByPlateVehiclesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchVehicleViewModel @Inject constructor(
    private val getByPlateVehiclesUseCase: GetByPlateVehiclesUseCase,
): ViewModel() {

    private val trigger = MutableStateFlow("")

    val vehicles : LiveData<Resource<List<Vehicle>>> =
            trigger
                .debounce(DEBOUNCE_TIME_IN_MILLIS)
                .asLiveData()
                .switchMap(::getVehiclesQuery)

    private fun getVehiclesQuery(query: @JvmSuppressWildcards String) = liveData {

        if (query.isEmpty()) {
            emit(Resource.Success(emptyList()))
            return@liveData
        }

        emit(Resource.Loading)
        val vehicles = getByPlateVehiclesUseCase.invoke(query)
        emit(vehicles)
    }

    fun setQuery(query: String?) {
        query ?: return
        trigger.value = query
    }

    companion object {
        private const val DEBOUNCE_TIME_IN_MILLIS = 300L
    }
}