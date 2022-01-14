package com.briolink.permission.exception.notfound

import com.briolink.permission.exception.ExceptionInterface
import org.springframework.http.HttpStatus

abstract class AbstractNotFoundException() : RuntimeException(), ExceptionInterface {
    override val httpsStatus = HttpStatus.NO_CONTENT
}
