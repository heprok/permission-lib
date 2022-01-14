package com.briolink.permission.model

import com.briolink.permission.enumeration.PermissionRightEnum
import com.briolink.permission.enumeration.PermissionRoleEnum

data class UserPermissionRights(
    val permissionRole: PermissionRoleEnum,
    val permissionRights: List<PermissionRightEnum>,
)
