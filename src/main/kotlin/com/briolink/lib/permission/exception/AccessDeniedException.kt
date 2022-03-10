package com.briolink.lib.permission.exception

import org.springframework.http.HttpStatus

class AccessDeniedException() : RuntimeException(), ExceptionInterface {
    override val httpsStatus = HttpStatus.FORBIDDEN
    override val code: String = "permission.access-denied"
}
