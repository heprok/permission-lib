package com.briolink.lib.permission.exception.notfound

import com.briolink.lib.common.exception.base.BaseNotFoundException

class PermissionRightNotFoundException() : BaseNotFoundException() {
    override val code: String = "permission-right.not-found"
}
