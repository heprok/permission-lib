package com.briolink.permission.model

import com.briolink.permission.dto.AccessObjectTypeDto
import com.briolink.permission.dto.UserPermissionRoleDto
import com.briolink.permission.enumeration.PermissionRoleEnum
import java.util.UUID

data class UserPermissionRole(
    var id: UUID,
    var userId: UUID,
    var permissionRole: PermissionRoleEnum,
    var accessObjectType: AccessObjectTypeDto,
    var accessObjectId: UUID,
) {
    companion object {
        fun fromDto(dto: UserPermissionRoleDto) = UserPermissionRole(
            id = dto.id,
            userId = dto.userId,
            permissionRole = PermissionRoleEnum.ofId(dto.role.id),
            accessObjectType = dto.accessObjectType,
            accessObjectId = dto.accessObjectId,
        )
    }
}
