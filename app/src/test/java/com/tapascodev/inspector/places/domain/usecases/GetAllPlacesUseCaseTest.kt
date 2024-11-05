package com.tapascodev.inspector.places.domain.usecases

import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.places.domain.PlaceRepository
import com.tapascodev.inspector.places.domain.model.Place
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class GetAllPlacesUseCaseTest{

    @RelaxedMockK
    private lateinit var placeRepository: PlaceRepository

    lateinit var getAllPlacesUseCase: GetAllPlacesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getAllPlacesUseCase = GetAllPlacesUseCase(placeRepository)
    }

    @Test
    fun `then get values from api` () = runBlocking {
        //Given
        val myList = Resource.Success(
            listOf( Place("1", 1,1, "S", null))
        )
        coEvery { placeRepository.getPlaces() } returns myList
        
        //When
        val response = getAllPlacesUseCase()
        
        //Then
        assert( myList === response)
    }

}