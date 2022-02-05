package com.briolink.lib.permission.exception.notfound

class UserPermissionRoleNotFoundException() : AbstractNotFoundException() {
    override val code: String = "user-permission-role.not-found"
}
