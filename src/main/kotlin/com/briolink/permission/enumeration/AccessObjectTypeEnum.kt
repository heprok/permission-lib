package com.briolink.permission.enumeration

import com.fasterxml.jackson.annotation.JsonValue

enum class AccessObjectTypeEnum(@JsonValue val id: Int) {
    Company(1),
    CompanyService(2);

    companion object {
        private val map = values().associateBy(AccessObjectTypeEnum::id)
        fun ofId(id: Int): AccessObjectTypeEnum = map[id]!!
    }
}
