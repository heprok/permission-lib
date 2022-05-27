package com.briolink.lib.permission.exception

import com.briolink.lib.common.exception.base.BaseAccessDeniedException
import org.springframework.http.HttpStatus

class AccessDeniedException() : BaseAccessDeniedException() {
    override val httpsStatus = HttpStatus.FORBIDDEN
    override val code: String = "permission.access-denied"
}
