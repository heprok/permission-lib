package com.briolink.lib.permission.exception.exist

import com.briolink.lib.common.exception.base.BaseExistException

class PermissionRoleExistException() : BaseExistException() {
    override val code: String = "permission-role.exist"
}
