package com.briolink.permission.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "permission-service.api")
@Suppress("ConfigurationProperties")
data class BlPermissionConfigurationProperties(
    val url: String,
    val version: String
)
