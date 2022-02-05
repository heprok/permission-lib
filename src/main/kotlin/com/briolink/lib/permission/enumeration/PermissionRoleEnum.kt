package com.briolink.lib.permission.enumeration

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue

enum class PermissionRoleEnum(@JsonValue val id: Int) {
    @JsonProperty("1")
    Owner(1),
    @JsonProperty("2")
    Admin(2),
    @JsonProperty("3")
    Superuser(3),
    @JsonProperty("4")
    Employee(4);

    companion object {
        private val map = values().associateBy(PermissionRoleEnum::id)
        fun ofId(id: Int): PermissionRoleEnum = map[id]!!
    }
}
