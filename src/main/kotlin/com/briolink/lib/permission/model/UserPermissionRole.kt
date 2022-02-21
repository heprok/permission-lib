package com.briolink.lib.permission.model

import com.briolink.lib.permission.dto.UserPermissionRoleDto
import com.briolink.lib.permission.enumeration.AccessObjectTypeEnum
import com.briolink.lib.permission.enumeration.PermissionRoleEnum
import java.util.UUID

data class UserPermissionRole(
    var id: UUID,
    var userId: UUID,
    var permissionRole: PermissionRoleEnum,
    var accessObjectType: AccessObjectTypeEnum,
    var accessObjectId: UUID,
) {
    companion object {
        fun fromDto(dto: UserPermissionRoleDto) = UserPermissionRole(
            id = dto.id,
            userId = dto.userId,
            permissionRole = PermissionRoleEnum.ofId(dto.role.id),
            accessObjectType = AccessObjectTypeEnum.ofId(dto.accessObjectType.id),
            accessObjectId = dto.accessObjectId,
        )
    }
}
