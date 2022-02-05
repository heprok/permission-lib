package com.briolink.lib.permission.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponseDto(
    @JsonProperty
    val id: String? = null,
    @JsonProperty
    val exceptionId: String? = null,
    @JsonProperty
    val error: String? = null,
    @JsonProperty
    val message: String? = null,
    @JsonProperty
    val status: Int,
)
