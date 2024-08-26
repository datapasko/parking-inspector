package com.tapascodev.inspector.lines.domain

import com.tapascodev.inspector.network.domain.Resource

interface LineRepository {
    suspend fun getLines() : Resource<List<LineModel>>
    suspend fun createLine(lineModel: LineModel) : Resource<Boolean>
}