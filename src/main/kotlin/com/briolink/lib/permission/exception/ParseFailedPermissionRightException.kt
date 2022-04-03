package com.briolink.lib.permission.exception

class ParseFailedPermissionRightException(val value: String) : RuntimeException() {
    override val message: String = "$value don`t parse must be 2 arguments: PermissionRight@AccessObjectType"
}
