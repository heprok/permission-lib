package com.briolink.lib.permission

import com.briolink.lib.common.util.BlSecurityUtil
import com.briolink.lib.permission.exception.AccessDeniedException
import com.briolink.lib.permission.exception.AllowRightMustBeArgAccessObjectIdException
import com.briolink.lib.permission.service.PermissionService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.security.access.prepost.PreAuthorize
import java.util.Arrays
import java.util.UUID
import java.util.stream.Collectors

@Target(AnnotationTarget.FUNCTION)
@MustBeDocumented
annotation class AllowedRights(
    val value: Array<String>,
    val argumentNameId: String = "accessObjectId",
    val operatorAnd: Boolean = true,
)

@Aspect
class AllowedRightAspect(
    private val permissionService: PermissionService
) {

    @PreAuthorize("isAuthenticated()")
    @Around("@annotation(com.briolink.lib.permission.AllowedRights)")
    @Throws(Throwable::class)
    fun doSomething(jp: ProceedingJoinPoint): Any {
        val rights: MutableSet<String>? = Arrays.stream(
            (jp.signature as MethodSignature).method
                .getAnnotation(AllowedRights::class.java).value,
        ).collect(Collectors.toSet())

        val operatorAnd: Boolean =
            (jp.signature as MethodSignature).method.getAnnotation(AllowedRights::class.java).operatorAnd

        val argumentNameId: String =
            (jp.signature as MethodSignature).method.getAnnotation(AllowedRights::class.java).argumentNameId

        var accessObjectId: UUID? = null
        (jp.signature as MethodSignature).parameterNames.forEachIndexed { index, it ->
            if (it == argumentNameId) {
                accessObjectId = UUID.fromString(jp.args[index] as String)
                jp.args[index]
            }
        }

        if (accessObjectId == null) throw AllowRightMustBeArgAccessObjectIdException((jp.signature as MethodSignature).method.name)

        if (rights != null) {
            for (right in rights) {
                permissionService.checkPermission(
                    userId = BlSecurityUtil.currentUserId,
                    accessObjectId = accessObjectId!!,
                    right = right,
                ).let {
                    if (operatorAnd && !it) throw AccessDeniedException()
                    if (!operatorAnd && it) return jp.proceed()
                    if (operatorAnd && rights.last() == right) return jp.proceed()
                }
            }
        }
        throw AccessDeniedException()
    }
}
