package com.briolink.lib.permission.exception.notfound

import com.briolink.lib.common.exception.base.BaseNotFoundException

class AccessObjectTypeNotFoundException() : BaseNotFoundException() {
    override val code: String = "access-object-type.not-found"
}
