package com.briolink.permission.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class UserPermissionRoleDto(
    @JsonProperty
    var id: UUID,
    @JsonProperty
    var userId: UUID,
    @JsonProperty
    var role: PermissionRoleDto,
    @JsonProperty
    var accessObjectType: AccessObjectTypeDto,
    @JsonProperty
    var accessObjectId: UUID,
)
