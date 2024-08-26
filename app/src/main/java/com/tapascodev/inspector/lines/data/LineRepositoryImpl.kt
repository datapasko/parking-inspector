package com.tapascodev.inspector.lines.data

import com.tapascodev.inspector.lines.domain.LineModel
import com.tapascodev.inspector.lines.domain.LineRepository
import com.tapascodev.inspector.network.data.FirebaseClient
import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.network.domain.SafeApiCall
import javax.inject.Inject


class LineRepositoryImpl @Inject constructor(
    private val firebaseClient: FirebaseClient
) : LineRepository, SafeApiCall {

    override suspend fun createLine(lineModel: LineModel): Resource<Boolean> = safeApiCall {
        val data = hashMapOf(
            "name" to lineModel.name,
            "type" to lineModel.type,
            "zone" to lineModel.zone
        )

        firebaseClient.db.collection("lines")
            .add(data)
            .isComplete
    }

    override suspend fun getLines(): Resource<List<LineModel>> = safeApiCall {
        val list = mutableListOf<LineModel>()
        firebaseClient.db.collection("lines").get()
            .addOnSuccessListener{ result ->
                for (document in result){
                    list.add(
                        LineResponse(
                            document.id,
                            document.getString("name"),
                            document.getString("type"),
                            document.getString("zone"),
                        ).toDomain()
                    )
                }
            }
        list
    }
}