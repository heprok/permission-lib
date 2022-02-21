package com.briolink.lib.permission.enumeration

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue

enum class PermissionRightEnum(@JsonValue val id: Int) {
    @JsonProperty("1")
    IsCanEditOwner(1),

    @JsonProperty("2")
    IsCanEditAdmin(2),

    @JsonProperty("3")
    IsCanEditSuperuser(3),

    @JsonProperty("4")
    IsCanEditCompanyProfile(4),

    @JsonProperty("5")
    IsCanEditEmployees(5),

    @JsonProperty("6")
    IsCanEditProject(6),

    @JsonProperty("7")
    IsCanEditCompanyService(7),

    @JsonProperty("8")
    IsCanEditNeedsExchange(8),

    @JsonProperty("9")
    IsCanCreateProject(9);

    companion object {
        private val map = values().associateBy(PermissionRightEnum::id)
        fun ofId(id: Int): PermissionRightEnum = map[id]!!
    }
}
