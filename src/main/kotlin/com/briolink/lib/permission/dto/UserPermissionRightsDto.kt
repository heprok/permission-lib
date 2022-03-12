package com.briolink.lib.permission.dto

import com.briolink.lib.permission.enumeration.AccessObjectTypeEnum
import com.briolink.lib.permission.enumeration.PermissionRightEnum
import com.briolink.lib.permission.enumeration.PermissionRoleEnum
import java.util.UUID

data class UserPermissionRightsDto(
    val accessObjectType: AccessObjectTypeEnum,
    val accessObjectId: UUID,
    val permissionRole: PermissionRoleEnum,
    val permissionRights: List<PermissionRightEnum>,
    val userId: UUID,
)
