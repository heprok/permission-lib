package com.briolink.permission.service

import com.briolink.permission.dto.UserPermissionRoleDto
import com.briolink.permission.enumeration.AccessObjectTypeEnum
import com.briolink.permission.enumeration.PermissionRightEnum
import com.briolink.permission.enumeration.PermissionRoleEnum
import com.briolink.permission.exception.exist.PermissionRoleExistException
import com.briolink.permission.model.UserPermissionRole
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.util.UUID

@Service
class PermissionsService {
    private val permission_right_url = "user_permission_rights"
    private val permission_role_url = "user_permission_roles"
    final val logger = LoggerFactory.getLogger(this::class.java)
    val webClient = WebClient.create("http://192.168.0.60:8200/api/v1")

    fun getPermissionRole(
        userId: UUID,
        accessObjectId: UUID,
        accessObjectType: AccessObjectTypeEnum
    ): PermissionRoleEnum? {
        val userPermissionRole = webClient.get()
            .uri("/$permission_role_url/?accessObjectId=$accessObjectId&accessObjectType=${accessObjectType.name}&userId=$userId")
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

    fun isHavaPermission(
        userId: UUID,
        accessObjectType: AccessObjectTypeEnum,
        accessObjectId: UUID,
        permissionRight: PermissionRightEnum
    ): Boolean {
        val isHavePermission = webClient.get()
            .uri("/$permission_right_url/check-permission/?accessObjectId=$accessObjectId&accessObjectType=${accessObjectType.name}&userId=$userId&permissionRight=${permissionRight.name}")
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
            .uri("/$permission_role_url/?accessObjectId=$accessObjectId&accessObjectType=${accessObjectType.name}&userId=$userId&permissionRole=${permissionRole.name}")
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
            .uri("/$permission_role_url/?accessObjectId=$accessObjectId&accessObjectType=${accessObjectType.name}&userId=$userId&permissionRole=${permissionRole.name}")
            .retrieve()
            .onStatus({ it == HttpStatus.NO_CONTENT }, { throw PermissionRoleExistException() })
            .bodyToMono(UserPermissionRoleDto::class.java)
            .block()

        return userPermissionRoleDto?.let { UserPermissionRole.fromDto(it) }
    }
}