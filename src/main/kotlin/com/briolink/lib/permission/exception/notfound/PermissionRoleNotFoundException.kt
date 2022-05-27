package com.briolink.lib.permission.exception.notfound

import com.briolink.lib.common.exception.base.BaseNotFoundException

class PermissionRoleNotFoundException() : BaseNotFoundException() {
    override val code: String = "permission-role.not-found"
}
