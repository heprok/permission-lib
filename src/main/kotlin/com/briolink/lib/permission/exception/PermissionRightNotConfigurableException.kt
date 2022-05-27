package com.briolink.lib.permission.exception

import com.briolink.lib.common.exception.base.IBlException
import org.springframework.http.HttpStatus

class PermissionRightNotConfigurableException() : RuntimeException(), IBlException {
    override val httpsStatus = HttpStatus.CONFLICT
    override val arguments: Array<String>? = null
    override val code: String = "permission-right.not-configurable"
}
