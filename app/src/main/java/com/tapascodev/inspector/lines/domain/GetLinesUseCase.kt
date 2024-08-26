package com.tapascodev.inspector.lines.domain

import javax.inject.Inject

class GetLinesUseCase @Inject constructor(
    private val lineRepository: LineRepository
) {
    suspend operator fun invoke () = lineRepository.getLines()
}