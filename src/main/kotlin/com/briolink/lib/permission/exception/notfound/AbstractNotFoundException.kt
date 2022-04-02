package com.briolink.lib.permission.exception.notfound

import com.briolink.lib.permission.exception.ExceptionInterface
import org.springframework.http.HttpStatus

abstract class AbstractNotFoundException() : RuntimeException(), ExceptionInterface {
    override val httpsStatus = HttpStatus.NOT_FOUND
}
