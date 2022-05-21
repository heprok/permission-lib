package com.briolink.lib.permission.exception

import com.briolink.lib.common.exception.base.IBlException
import org.springframework.http.HttpStatus

class AllowRightMustBeArgAccessObjectIdException(methodName: String) : IBlException, RuntimeException() {
    override val arguments: Array<String>? = null
    override val code: String = ""
    override val httpsStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    override val message: String = "$methodName must be contain argument accessObjectId"
}
