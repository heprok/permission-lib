package com.briolink.lib.permission.dto

import com.briolink.lib.permission.enumeration.PermissionRoleEnum
import com.briolink.lib.permission.model.PermissionRight
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

data class ListUserPermissionRightDto(
    @JsonProperty
    private val listUserPermissionRightsDto: List<UserPermissionRightDto>
) {
    val userRole: PermissionRoleEnum
        get() = PermissionRoleEnum.ofId(listUserPermissionRightsDto.first().userRole.role.id)

    val rights: List<PermissionRight>
        get() = listUserPermissionRightsDto.map { PermissionRight(it.userRole.accessObjectType.name, it.right.name) }
}
