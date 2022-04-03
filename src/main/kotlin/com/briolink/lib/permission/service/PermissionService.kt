package com.briolink.lib.permission.service

import com.briolink.lib.permission.enumeration.AccessObjectTypeEnum
import com.briolink.lib.permission.enumeration.PermissionRoleEnum
import com.briolink.lib.permission.exception.exist.PermissionRoleExistException
import com.briolink.lib.permission.exception.notfound.PermissionRightNotFoundException
import com.briolink.lib.permission.exception.notfound.UserPermissionRoleNotFoundException
import com.briolink.lib.permission.model.PermissionRight
import com.briolink.lib.permission.model.UserPermissionRights
import com.briolink.lib.permission.model.UserPermissionRole
import java.util.UUID

class PermissionService(private val webClient: WebClientPermissionService) {
    private val permissionRightUrl = "user_permission_rights"
    private val permissionRoleUrl = "user_permission_roles"

    fun getPermissionRole(
        userId: UUID,
        accessObjectId: UUID,
        accessObjectType: String
    ): PermissionRoleEnum? {
        return try {
            webClient.getPermissionRole(
                userId = userId,
                accessObjectId = accessObjectId,
                accessObjectType = accessObjectType
            ).block()?.let {
                PermissionRoleEnum.ofId(it.role.id)
            }
        } catch (ex: UserPermissionRoleNotFoundException) {
            null
        }
    }

    fun setPermissionRights(
        userId: UUID,
        accessObjectId: UUID,
        accessObjectType: String,
        permissionRole: String,
        permissionRights: List<String>
    ): UserPermissionRights? {

        return webClient.setPermissionRights(
            userId = userId,
            accessObjectId = accessObjectId,
            accessObjectType = accessObjectType,
            permissionRole = permissionRole,
            permissionRights = permissionRights
        ).block()?.let {
            UserPermissionRights(
                permissionRole = it.userRole,
                permissionRights = it.rights,
            )
        }
    }

    fun getUserPermissionRights(
        userId: UUID,
        accessObjectId: UUID,
        accessObjectType: AccessObjectTypeEnum
    ): UserPermissionRights? {
        return try {
            webClient.getUserPermissionRights(
                userId = userId,
                accessObjectId = accessObjectId,
                accessObjectType = accessObjectType
            ).block()?.let {
                UserPermissionRights(
                    permissionRole = it.userRole,
                    permissionRights = it.rights,
                )
            }
        } catch (ex: PermissionRightNotFoundException) {
            null
        }
    }

    fun checkPermission(
        userId: UUID,
        accessObjectId: UUID,
        right: PermissionRight
    ): Boolean {
        return webClient.checkPermission(
            userId = userId,
            accessObjectId = accessObjectId,
            right = right
        ).block() ?: false
    }

    fun checkPermission(
        userId: UUID,
        accessObjectId: UUID,
        right: String
    ): Boolean {
        return checkPermission(userId, accessObjectId, PermissionRight.fromString(right))
    }

    @Throws(PermissionRoleExistException::class)
    fun createPermissionRole(
        userId: UUID,
        accessObjectType: AccessObjectTypeEnum,
        accessObjectId: UUID,
        permissionRole: PermissionRoleEnum
    ): UserPermissionRole? {
        return webClient.createPermissionRole(
            userId = userId,
            accessObjectType = accessObjectType,
            accessObjectId = accessObjectId,
            permissionRole = permissionRole

        ).block()?.let { UserPermissionRole.fromDto(it) }
    }

    fun editPermissionRole(
        userId: UUID,
        accessObjectType: AccessObjectTypeEnum,
        accessObjectId: UUID,
        permissionRole: PermissionRoleEnum
    ): UserPermissionRole? {
        return webClient.editPermissionRole(
            userId = userId,
            accessObjectType = accessObjectType,
            accessObjectId = accessObjectId,
            permissionRole = permissionRole
        ).block()?.let { UserPermissionRole.fromDto(it) }
    }

    fun deletePermissionRole(
        userId: UUID,
        accessObjectType: AccessObjectTypeEnum,
        accessObjectId: UUID,
    ): Boolean {
        return webClient.deletePermissionRole(
            userId = userId,
            accessObjectType = accessObjectType,
            accessObjectId = accessObjectId
        ).block() ?: false
    }
}
