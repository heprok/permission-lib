package com.briolink.lib.permission.exception

import com.briolink.lib.common.exception.base.IBlException
import org.springframework.http.HttpStatus

class ParseFailedPermissionRightException(value: String) : RuntimeException(), IBlException {
    override val arguments: Array<String>? = null
    override val code: String = ""
    override val httpsStatus: HttpStatus = HttpStatus.BAD_REQUEST
    override val message: String = "$value don`t parse must be 2 arguments: PermissionRight@AccessObjectType"
}
