package com.briolink.permission.exception.exist

class PermissionRightExistException() : AbstractExistsException() {
    override val code: String = "permission-right.permission-right.exist"
}
