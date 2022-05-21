package com.briolink.lib.permission.exception.exist

import com.briolink.lib.common.exception.base.BaseExistException

class PermissionRightExistException() : BaseExistException() {
    override val code: String = "permission-right.permission-right.exist"
}
