package com.briolink.lib.permission.exception

import org.springframework.http.HttpStatus

class PermissionRightNotConfigurableException() : RuntimeException(), ExceptionInterface {
    override val httpsStatus = HttpStatus.CONFLICT
    override val code: String = "permission-right.not-configurable"
}
