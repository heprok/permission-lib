# Permission library

## Installation

To connect to the project in gradle.kts connect the maven repository.

Dependent on event lib
```kotlin
repositories {
    maven {
        url = uri("https://gitlab.com/api/v4/projects/32844103/packages/maven")
        authentication {
            create<HttpHeaderAuthentication>("header")
        }
        credentials(HttpHeaderCredentials::class) {
            name = "Deploy-Token"
            value = System.getenv("CI_DEPLOY_PASSWORD")
        }
    }
}

dependencies {
    implementation("com.briolink.lib:permission")
}

```

In the project configuration add lines to connect to the api permission-service

```yaml
permission-service:
  api:
    url: https://permission-service.${env.spring_profiles_active}.svc.cluster.local/
    version: 1
```

You can now access com.briolink.lib.service.PermissionService

## Documentation

### Basic classes

[AllowedRights](https://gitlab.com/briolink/network/backend/permission-lib/-/blob/main/src/main/kotlin/com/briolink/lib/permission/AllowedRights.kt)
— Annotation checks if the user has rights to use the function

In function must be contained accessObjectId - String

[PermissionService](https://gitlab.com/briolink/network/backend/permission-lib/-/blob/main/src/main/kotlin/com/briolink/lib/permission/service/PermissionService.kt)
— Main service

[UserPermissionRights](https://gitlab.com/briolink/network/backend/permission-lib/-/blob/main/src/main/kotlin/com/briolink/lib/permission/model/UserPermissionRights.kt)
— Model about rights and role by user

[UserPermissionRole](https://gitlab.com/briolink/network/backend/permission-lib/-/blob/main/src/main/kotlin/com/briolink/lib/permission/model/UserPermissionRole.kt)
— Model about rights for the object

## Examples

### Annotation AllowedRights

If more than one value is specified in value, the function will execute when the user has one of these rights

When the user wants to update the company logo, the user must have the right IsCanEditCompanyProfile

```kotlin
    @AllowedRights(
    accessObjectType = AccessObjectTypeEnum.Company,
    value = [PermissionRightEnum.IsCanEditCompanyProfile]
)
fun uploadCompanyImage(
    @InputArgument("id") accessObjectId: String,
    @InputArgument("image") image: MultipartFile?
): URL {
    return companyService.uploadCompanyProfileImage(UUID.fromString(accessObjectId), image)
}
```

If User haven`t right this example will return
exception [AccessDeniedException](https://gitlab.com/briolink/network/backend/permission-lib/-/blob/main/src/main/kotlin/com/briolink/lib/permission/exception/AccessDeniedException.kt)

### Add permission role

Sets the role for a user with default rights

```kotlin
try {
    permissionService.createPermissionRole(
        userId = userId,
        accessObjectType = AccessObjectTypeEnum.Company,
        accessObjectId = companyId,
        permissionRole = PermissionRoleEnum.Owner,
    )?.also {
        updateUserPermission(userId, companyId)
    }
} catch (_: PermissionRoleExistException) {
}
```

### Check permission right

```kotlin
if (permissionService.isHavePermission(
        accessObjectType = AccessObjectTypeEnum.Company,
        userId = SecurityUtil.currentUserAccountId,
        accessObjectId = UUID.fromString(companyId),
        permissionRight = PermissionRightEnum.IsCanEditCompanyService
    )
) updateCompanyService(name = "New company service")
```