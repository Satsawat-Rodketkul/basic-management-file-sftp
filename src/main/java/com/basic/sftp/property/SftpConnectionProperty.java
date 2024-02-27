package com.basic.sftp.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sftp.connection")
@Getter
@Setter
public class SftpConnectionProperty {
    private String type;
    private String host;
    private Integer port;
    private String username;
    private String password;
}