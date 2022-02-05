package com.briolink.lib.permission.service

import com.briolink.lib.permission.dto.UserPermissionRoleDto
import com.briolink.lib.permission.enumeration.AccessObjectTypeEnum
import com.briolink.lib.permission.enumeration.PermissionRightEnum
import com.briolink.lib.permission.enumeration.PermissionRoleEnum
import com.briolink.lib.permission.exception.exist.PermissionRoleExistException
import com.briolink.lib.permission.model.UserPermissionRights
import com.briolink.lib.permission.model.UserPermissionRole
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import java.util.UUID

class PermissionService(private val webClient: WebClient) {
    private val permissionRightUrl = "user_permission_rights"
    private val permissionRoleUrl = "user_permission_roles"

    fun getPermissionRole(
        userId: UUID,
        accessObjectId: UUID,
        accessObjectType: AccessObjectTypeEnum
    ): PermissionRoleEnum? {
        val userPermissionRole = webClient.get()
            .uri("/$permissionRoleUrl/?accessObjectId=$accessObjectId&accessObjectType=${accessObjectType.name}&userId=$userId")
            .retrieve()
//            .onStatus(HttpStatus::is4xxClientError) {
//                it.bodyToMono(ErrorResponseDto::class.java)
//            }
//            .onStatus(HttpStatus::is4xxClientError) { _ ->
//                Mono.error<PermissionRoleNotFoundException>(PermissionRoleNotFoundException(userId, accessObjectId))
//            }
            .bodyToMono(UserPermissionRoleDto::class.java)
            .block()

        return userPermissionRole?.let { PermissionRoleEnum.ofId(it.role.id) }
    }

    fun getUserPermissionRights(
        userId: UUID,
        accessObjectId: UUID,
        accessObjectType: AccessObjectTypeEnum
    ): UserPermissionRights? {
        val listUserPermissionRightsDto = webClient.get()
            .uri("/$permissionRightUrl/?accessObjectId=$accessObjectId&accessObjectType=${accessObjectType.name}&userId=$userId")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(com.briolink.lib.permission.dto.ListUserPermissionRightDto::class.java)
            .block()

        return listUserPermissionRightsDto?.let {
            UserPermissionRights(
                permissionRole = listUserPermissionRightsDto.userRole,
                permissionRights = listUserPermissionRightsDto.rights,
            )
        }
    }

    fun isHavePermission(
        userId: UUID,
        accessObjectType: AccessObjectTypeEnum,
        accessObjectId: UUID,
        permissionRight: PermissionRightEnum
    ): Boolean {
        val isHavePermission = webClient.get()
            .uri("/$permissionRightUrl/check-permission/?accessObjectId=$accessObjectId&accessObjectType=${accessObjectType.name}&userId=$userId&permissionRight=${permissionRight.name}")
            .retrieve()
            .bodyToMono(Boolean::class.java)
            .block()

        return isHavePermission ?: false
    }

    @Throws(PermissionRoleExistException::class)
    fun createPermissionRole(
        userId: UUID,
        accessObjectType: AccessObjectTypeEnum,
        accessObjectId: UUID,
        permissionRole: PermissionRoleEnum
    ): UserPermissionRole? {
        val userPermissionRoleDto = webClient.post()
            .uri("/$permissionRoleUrl/?accessObjectId=$accessObjectId&accessObjectType=${accessObjectType.name}&userId=$userId&permissionRole=${permissionRole.name}")
            .retrieve()
            .onStatus({ it == HttpStatus.CONFLICT }, { throw PermissionRoleExistException() })
            .bodyToMono(UserPermissionRoleDto::class.java)
            .block()

        return userPermissionRoleDto?.let { UserPermissionRole.fromDto(it) }
    }

    fun editPermissionRole(
        userId: UUID,
        accessObjectType: AccessObjectTypeEnum,
        accessObjectId: UUID,
        permissionRole: PermissionRoleEnum
    ): UserPermissionRole? {
        val userPermissionRoleDto = webClient.put()
            .uri("/$permissionRoleUrl/?accessObjectId=$accessObjectId&accessObjectType=${accessObjectType.name}&userId=$userId&permissionRole=${permissionRole.name}")
            .retrieve().onStatus({ it == HttpStatus.NO_CONTENT }, { throw PermissionRoleExistException() })
            .bodyToMono(UserPermissionRoleDto::class.java).block()

        return userPermissionRoleDto?.let { UserPermissionRole.fromDto(it) }
    }

    fun deletePermissionRole(
        userId: UUID,
        accessObjectType: AccessObjectTypeEnum,
        accessObjectId: UUID,
    ): Boolean {
        val isDeleted = webClient.delete()
            .uri("/$permissionRoleUrl/?accessObjectId=$accessObjectId&accessObjectType=${accessObjectType.name}&userId=$userId")
            .retrieve().onStatus({ it == HttpStatus.NO_CONTENT }, { throw PermissionRoleExistException() })
            .bodyToMono(Boolean::class.java).block()

        return isDeleted ?: false
    }
}
