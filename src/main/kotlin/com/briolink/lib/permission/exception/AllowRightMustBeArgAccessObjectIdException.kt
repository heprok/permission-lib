package com.briolink.lib.permission.exception

class AllowRightMustBeArgAccessObjectIdException(methodName: String) : RuntimeException() {
    override val message: String = "$methodName must be contain argument accessObjectId"
}
