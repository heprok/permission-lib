package com.briolink.permission.exception.exist

class PermissionRoleExistException() : AbstractExistsException() {
    override val code: String = "permission-role.exist"
}
