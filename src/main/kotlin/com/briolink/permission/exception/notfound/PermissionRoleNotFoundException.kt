package com.briolink.permission.exception.notfound

class PermissionRoleNotFoundException() : AbstractNotFoundException() {
    override val code: String = "permission-role.not-found"
}
