package com.briolink.lib.permission.enumeration

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue

enum class AccessObjectTypeEnum(@JsonValue val id: Int) {
    @JsonProperty("1")
    Company(1),

    @JsonProperty("2")
    CompanyService(2);

    companion object {
        private val map = values().associateBy(AccessObjectTypeEnum::id)
        @JsonCreator(mode = JsonCreator.Mode.DELEGATING) @JvmStatic
        fun ofId(id: Int): AccessObjectTypeEnum = map[id]!!
    }
}
