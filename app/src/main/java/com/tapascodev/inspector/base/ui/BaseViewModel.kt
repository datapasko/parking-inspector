package com.tapascodev.inspector.base.ui

import androidx.lifecycle.ViewModel
import com.tapascodev.inspector.base.domain.BaseRepository

abstract class BaseViewModel (
    private val repository: BaseRepository
) : ViewModel() {
}