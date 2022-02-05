package com.briolink.lib.permission.exception.notfound

class PermissionRoleNotFoundException() : AbstractNotFoundException() {
    override val code: String = "permission-role.not-found"
}
