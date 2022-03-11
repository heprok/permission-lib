package com.briolink.lib.permission

import com.briolink.lib.permission.enumeration.AccessObjectTypeEnum
import com.briolink.lib.permission.enumeration.PermissionRightEnum
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
    val accessObjectType: AccessObjectTypeEnum,
    val value: Array<PermissionRightEnum>
)

@Aspect
class AllowedRightAspect(
    private val permissionService: PermissionService
) {

    @PreAuthorize("isAuthenticated()")
    @Around("@annotation(com.briolink.lib.permission.AllowedRights)")
    @Throws(Throwable::class)
    fun doSomething(jp: ProceedingJoinPoint): Any {
        val rights: MutableSet<PermissionRightEnum>? = Arrays.stream(
            (jp.signature as MethodSignature).method
                .getAnnotation(AllowedRights::class.java).value,
        ).collect(Collectors.toSet())

        val accessObjectType: AccessObjectTypeEnum =
            (jp.signature as MethodSignature).method.getAnnotation(AllowedRights::class.java).accessObjectType

        var accessObjectId: UUID? = null
        (jp.signature as MethodSignature).parameterNames.forEachIndexed { index, it ->
            if (it == "accessObjectId")
                accessObjectId = UUID.fromString(jp.args[index] as String)
        }

        if(accessObjectId == null ) throw AllowRightMustBeArgAccessObjectIdException((jp.signature as MethodSignature).method.name)

        if (rights != null) {
            for (right in rights) {
                if (permissionService.isHavePermission(
                        userId = SecurityUtil.currentUserAccountId,
                        accessObjectType = accessObjectType,
                        accessObjectId = accessObjectId!!,
                        permissionRight = right,
                    )
                ) {
                    return jp.proceed()
                }
            }
        }
        throw AccessDeniedException()
    }
}
