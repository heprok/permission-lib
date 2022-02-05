package com.briolink.lib.permission.exception.exist

import com.briolink.lib.permission.exception.ExceptionInterface
import org.springframework.http.HttpStatus

abstract class AbstractExistsException() : RuntimeException(), ExceptionInterface {
    override val httpsStatus = HttpStatus.CONFLICT
}
