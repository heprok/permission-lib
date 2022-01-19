package com.briolink.permission.model

import com.briolink.permission.enumeration.PermissionRightEnum
import com.briolink.permission.enumeration.PermissionRoleEnum
import com.fasterxml.jackson.annotation.JsonProperty

data class UserPermissionRights(
    @JsonProperty
    val permissionRole: PermissionRoleEnum,
    @JsonProperty
    val permissionRights: List<PermissionRightEnum>,
)
