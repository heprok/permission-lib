package com.briolink.lib.permission.service

import com.briolink.lib.permission.dto.ListUserPermissionRightDto
import com.briolink.lib.permission.dto.UserPermissionRoleDto
import com.briolink.lib.permission.enumeration.AccessObjectTypeEnum
import com.briolink.lib.permission.enumeration.PermissionRoleEnum
import com.briolink.lib.permission.exception.ErrorResponse
import com.briolink.lib.permission.exception.exist.PermissionRoleExistException
import com.briolink.lib.permission.exception.notfound.PermissionRightNotFoundException
import com.briolink.lib.permission.exception.notfound.UserPermissionRoleNotFoundException
import com.briolink.lib.permission.model.PermissionRight
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.UUID

open class WebClientPermissionService(private val webClient: WebClient) {

    protected open val permissionRightUrl = "user_permission_rights"
    protected open val permissionRoleUrl = "user_permission_roles"

    open fun getPermissionRole(
        userId: UUID,
        accessObjectId: UUID,
        accessObjectType: String
    ): Mono<UserPermissionRoleDto> {

        return webClient.get()
            .uri { builder ->
                builder.path("/$permissionRoleUrl/")
                    .queryParam("accessObjectId", accessObjectId)
                    .queryParam("accessObjectType", accessObjectType)
                    .queryParam("userId", userId)
                    .build()
            }
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError) { response ->
                return@onStatus response.bodyToMono(ErrorResponse::class.java).flatMap { error ->
                    return@flatMap convertErrorResponseToMonoErrorAtRole(error)
                }
            }
            .bodyToMono(UserPermissionRoleDto::class.java)
    }

    open fun setPermissionRights(
        userId: UUID,
        accessObjectId: UUID,
        accessObjectType: String,
        permissionRole: String,
        permissionRights: List<String>
    ): Mono<ListUserPermissionRightDto> {
        return webClient.post()
            .uri { builder ->
                builder.path("/$permissionRightUrl/")
                    .queryParam("accessObjectType", accessObjectType)
                    .queryParam("accessObjectId", accessObjectId)
                    .queryParam("permissionRole", permissionRole)
                    .queryParam("userId", userId)
                    .queryParam("permissionRights", permissionRights.map { it.split("@")[0] })
                    .build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError) { response ->
                return@onStatus response.bodyToMono(ErrorResponse::class.java).flatMap { error ->
                    return@flatMap convertErrorResponseToMonoErrorAtRights(error)
                }
            }
            .bodyToMono(ListUserPermissionRightDto::class.java)
    }

    open fun getUserPermissionRights(
        userId: UUID,
        accessObjectId: UUID,
        accessObjectType: AccessObjectTypeEnum
    ): Mono<ListUserPermissionRightDto> {
        return webClient.get()
            .uri { builder ->
                builder.path("/$permissionRightUrl/")
                    .queryParam("accessObjectId", accessObjectId)
                    .queryParam("accessObjectType", accessObjectType.name)
                    .queryParam("userId", userId)
                    .build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError) { response ->
                return@onStatus response.bodyToMono(ErrorResponse::class.java).flatMap { error ->
                    return@flatMap convertErrorResponseToMonoErrorAtRights(error)
                }
            }
            .bodyToMono(ListUserPermissionRightDto::class.java)
    }

    open fun checkPermission(
        userId: UUID,
        accessObjectId: UUID,
        right: PermissionRight
    ): Mono<Boolean> {
        return webClient.get()
            .uri { builder ->
                builder.path("/$permissionRightUrl/check-permission/")
                    .queryParam("accessObjectId", accessObjectId)
                    .queryParam("accessObjectType", right.accessObjectType)
                    .queryParam("userId", userId)
                    .queryParam("permissionRight", right.action)
                    .build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError) { response ->
                return@onStatus response.bodyToMono(ErrorResponse::class.java).flatMap { error ->
                    return@flatMap convertErrorResponseToMonoErrorAtRights(error)
                }
            }
            .bodyToMono(Boolean::class.java)
    }

    open fun checkPermission(
        userId: UUID,
        accessObjectId: UUID,
        right: String
    ): Mono<Boolean> {
        return checkPermission(userId, accessObjectId, PermissionRight.fromString(right))
    }

    @Throws(PermissionRoleExistException::class)
    open fun createPermissionRole(
        userId: UUID,
        accessObjectType: AccessObjectTypeEnum,
        accessObjectId: UUID,
        permissionRole: PermissionRoleEnum
    ): Mono<UserPermissionRoleDto> {
        return webClient.post()
            .uri { builder ->
                builder.path("/$permissionRoleUrl/")
                    .queryParam("accessObjectId", accessObjectId)
                    .queryParam("accessObjectType", accessObjectType.name)
                    .queryParam("userId", userId)
                    .queryParam("permissionRole", permissionRole.name)
                    .build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError) { response ->
                return@onStatus response.bodyToMono(ErrorResponse::class.java).flatMap { error ->
                    return@flatMap convertErrorResponseToMonoErrorAtRole(error)
                }
            }
            .bodyToMono(UserPermissionRoleDto::class.java)
    }

    protected open fun <T : Exception> convertErrorResponseToMonoErrorAtRights(response: ErrorResponse): Mono<T> {
        return when (response.status) {
            HttpStatus.FORBIDDEN.value() -> Mono.error(AccessDeniedException("Not auth"))
            HttpStatus.NOT_ACCEPTABLE.value() -> Mono.error(RuntimeException(response.message))
            HttpStatus.NOT_FOUND.value() -> Mono.error(PermissionRightNotFoundException())
            else -> Mono.error(RuntimeException(response.message))
        }
    }

    protected open fun <T : Exception> convertErrorResponseToMonoErrorAtRole(response: ErrorResponse): Mono<T> {
        return when (response.status) {
            HttpStatus.FORBIDDEN.value() -> Mono.error(AccessDeniedException("Not auth"))
            HttpStatus.NOT_ACCEPTABLE.value() -> Mono.error(RuntimeException(response.message))
            HttpStatus.NOT_FOUND.value() -> Mono.error(UserPermissionRoleNotFoundException())
            HttpStatus.CONFLICT.value() -> Mono.error(PermissionRoleExistException())
            else -> Mono.error(RuntimeException(response.message))
        }
    }

    open fun editPermissionRole(
        userId: UUID,
        accessObjectType: AccessObjectTypeEnum,
        accessObjectId: UUID,
        permissionRole: PermissionRoleEnum
    ): Mono<UserPermissionRoleDto> {
        return webClient.put()
            .uri { builder ->
                builder.path("/$permissionRoleUrl/")
                    .queryParam("accessObjectId", accessObjectId)
                    .queryParam("accessObjectType", accessObjectType.name)
                    .queryParam("userId", userId)
                    .queryParam("permissionRole", permissionRole.name)
                    .build()
            }
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError) { response ->
                return@onStatus response.bodyToMono(ErrorResponse::class.java).flatMap { error ->
                    return@flatMap convertErrorResponseToMonoErrorAtRole(error)
                }
            }
            .bodyToMono(UserPermissionRoleDto::class.java)
    }

    open fun deletePermissionRole(
        userId: UUID,
        accessObjectType: AccessObjectTypeEnum,
        accessObjectId: UUID,
    ): Mono<Boolean> {
        return webClient.delete()
            .uri { builder ->
                builder.path("/$permissionRoleUrl/")
                    .queryParam("accessObjectId", accessObjectId)
                    .queryParam("accessObjectType", accessObjectType.name)
                    .queryParam("userId", userId)
                    .build()
            }
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError) { response ->
                return@onStatus response.bodyToMono(ErrorResponse::class.java).flatMap { error ->
                    return@flatMap convertErrorResponseToMonoErrorAtRole(error)
                }
            }
            .bodyToMono(Boolean::class.java)
    }
}
