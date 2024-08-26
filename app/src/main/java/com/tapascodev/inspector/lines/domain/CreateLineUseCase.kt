package com.tapascodev.inspector.lines.domain

import javax.inject.Inject

class CreateLineUseCase @Inject constructor(
    private val lineRepository: LineRepository
) {
    suspend operator fun invoke(lineModel: LineModel) = lineRepository.createLine(lineModel)
}