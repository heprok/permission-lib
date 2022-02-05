package com.briolink.lib.permission.dto

import com.fasterxml.jackson.annotation.JsonProperty

class PermissionRoleDto(
    @JsonProperty
    var id: Int,
    @JsonProperty
    var name: String,
    @JsonProperty
    var level: Int,
)
