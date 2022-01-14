package com.briolink.permission.exception.exist

import com.briolink.permission.exception.ExceptionInterface
import org.springframework.http.HttpStatus

abstract class AbstractExistsException() : RuntimeException(), ExceptionInterface {
    override val httpsStatus = HttpStatus.CONFLICT
}
