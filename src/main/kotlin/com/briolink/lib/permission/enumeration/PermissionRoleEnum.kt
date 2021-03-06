package com.briolink.lib.permission.enumeration

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue

enum class PermissionRoleEnum(@JsonValue val id: Int, val level: Int) {
    @JsonProperty("1")
    Owner(1, 1),

    @JsonProperty("2")
    Admin(2, 2),

    @JsonProperty("3")
    Superuser(3, 3),

    @JsonProperty("4")
    Employee(4, 4);

    companion object {
        private val mapId = values().associateBy(PermissionRoleEnum::id)
        @JsonCreator(mode = JsonCreator.Mode.DELEGATING) @JvmStatic
        fun ofId(id: Int): PermissionRoleEnum = mapId[id]!!
    }
}
