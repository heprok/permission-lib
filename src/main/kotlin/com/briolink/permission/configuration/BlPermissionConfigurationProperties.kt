package com.briolink.permission.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("permission")
@Suppress("ConfigurationProperties")
data class BlPermissionConfigurationProperties(
    val permissionServiceApi: String? = null,
)
