package com.briolink.lib.permission.exception.notfound

import com.briolink.lib.common.exception.base.BaseNotFoundException

class UserPermissionRoleNotFoundException() : BaseNotFoundException() {
    override val code: String = "user-permission-role.not-found"
}
