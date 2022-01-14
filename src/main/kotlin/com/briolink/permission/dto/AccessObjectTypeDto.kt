package com.briolink.permission.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class AccessObjectTypeDto(
    @JsonProperty
    var id: Int,
    @JsonProperty
    var name: String,
)
