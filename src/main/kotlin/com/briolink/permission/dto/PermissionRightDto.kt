package com.briolink.permission.dto

import com.fasterxml.jackson.annotation.JsonProperty

class PermissionRightDto(
    @JsonProperty
    var id: Int,
    @JsonProperty
    var name: String,
)
