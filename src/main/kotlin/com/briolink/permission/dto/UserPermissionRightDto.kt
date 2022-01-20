package com.briolink.permission.dto

import com.briolink.permission.enumeration.PermissionRightEnum
import com.briolink.permission.enumeration.PermissionRoleEnum
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

    val rights: List<PermissionRightEnum>
        get() = listUserPermissionRightsDto.map { PermissionRightEnum.ofId(it.right.id) }
}
