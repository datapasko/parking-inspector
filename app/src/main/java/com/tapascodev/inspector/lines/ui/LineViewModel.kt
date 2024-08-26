package com.tapascodev.inspector.lines.ui

import androidx.lifecycle.ViewModel
import com.tapascodev.inspector.lines.domain.CreateLineUseCase
import com.tapascodev.inspector.lines.domain.GetLinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LineViewModel @Inject constructor(
    private val getLinesUseCase: GetLinesUseCase,
    private val createLineUseCase: CreateLineUseCase
) : ViewModel() {

    /*private var _lines = MutableStateFlow<Result<>(List<LineModel>, DataError.Network)>
    val lines: StateFlow<Resource<List<LineModel>>> = _lines

    private var _creation = MutableStateFlow<Resource<String>>(Resource.Loading)
    val creation: StateFlow<Resource<String>> = _creation

    fun getLines() {
        viewModelScope.launch {
            _lines.value = Resource.Loading
            _lines.value = getLinesUseCase.invoke()
        }
    }

    fun creationLine(lineModel: LineModel) {
        viewModelScope.launch {
            _creation.value = Resource.Loading
            _creation.value = createLineUseCase.invoke(lineModel)
        }
    }*/
}