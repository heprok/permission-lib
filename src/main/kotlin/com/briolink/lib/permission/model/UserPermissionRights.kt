package com.briolink.lib.permission.model

import com.briolink.lib.permission.enumeration.PermissionRightEnum
import com.briolink.lib.permission.enumeration.PermissionRoleEnum
import com.fasterxml.jackson.annotation.JsonProperty

data class UserPermissionRights(
    @JsonProperty
    val permissionRole: PermissionRoleEnum,
    @JsonProperty
    val permissionRights: List<PermissionRightEnum>,
)
