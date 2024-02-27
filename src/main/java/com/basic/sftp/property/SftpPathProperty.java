package com.basic.sftp.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sftp.url")
@Getter
@Setter
public class SftpPathProperty {
    private String path;
}
