package com.briolink.permission.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class UserPermissionRightDto(
    @JsonProperty
    var id: UUID,
    @JsonProperty
    var userRole: UserPermissionRoleDto,
    @JsonProperty
    var right: PermissionRightDto,
    @JsonProperty
    var enabled: Boolean,
)
