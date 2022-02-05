package com.briolink.lib.permission.exception.exist

class PermissionRoleExistException() : AbstractExistsException() {
    override val code: String = "permission-role.exist"
}
