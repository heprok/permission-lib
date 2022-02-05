package com.briolink.lib.permission.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

class DefaultPermissionRightDto(
    @JsonProperty
    var id: UUID,
    @JsonProperty
    var userRole: PermissionRoleDto,
    @JsonProperty
    var right: PermissionRightDto,
    @JsonProperty
    var accessObjectType: AccessObjectTypeDto,
    @JsonProperty
    var enabled: Boolean,
    @JsonProperty
    var configurable: Boolean,
)
