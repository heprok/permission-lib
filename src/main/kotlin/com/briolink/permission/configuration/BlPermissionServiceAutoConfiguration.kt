package com.briolink.permission.configuration

import com.briolink.permission.service.PermissionService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@ComponentScan("com.briolink.permission.service")
@EnableConfigurationProperties(
    BlPermissionConfigurationProperties::class
)
@ConditionalOnProperty(prefix = "permission-service.api", name = ["url", "version"])
class BlPermissionServiceAutoConfiguration {
    @Value("\${permission-service.api.url}")
    lateinit var urlApi: String

    @Value("\${permission-service.api.version}")
    lateinit var apiVersion: String

    @Bean
    @Primary
    fun permissionService() = PermissionService(WebClient.create("$urlApi/api/v$apiVersion/"))
}
