package com.briolink.lib.permission.dto

import com.briolink.lib.permission.enumeration.AccessObjectTypeEnum
import com.briolink.lib.permission.enumeration.PermissionRoleEnum
import com.briolink.lib.permission.model.PermissionRight
import java.util.UUID

data class UserPermissionRightsDto(
    val accessObjectType: AccessObjectTypeEnum,
    val accessObjectId: UUID,
    val permissionRole: PermissionRoleEnum,
    val permissionRights: List<PermissionRight>,
    val userId: UUID,
)
