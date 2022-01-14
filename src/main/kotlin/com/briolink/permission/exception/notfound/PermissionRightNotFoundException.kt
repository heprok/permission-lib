package com.briolink.permission.exception.notfound

class PermissionRightNotFoundException() : AbstractNotFoundException() {
    override val code: String = "permission-right.not-found"
}
