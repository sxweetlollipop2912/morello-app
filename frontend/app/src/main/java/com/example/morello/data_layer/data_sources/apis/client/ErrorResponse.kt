package com.example.morello.data_layer.data_sources.apis.client

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.ResponseBody

data class ErrorResponse(
    @JsonProperty("message") val message: String,
) {
    companion object {
        fun fromResponseBody(errorBody: ResponseBody?): ErrorResponse? {
            val mapper = ObjectMapper()
            val mappedBody = errorBody?.let {
                mapper.readValue(it.toString(), ErrorResponse::class.java)
            }
            return mappedBody
        }
    }
}
