package com.basic.sftp.models;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import lombok.*;

@Getter
@Setter
public class SftpConnectionModel {
    private Session session;
    private ChannelSftp channel;
}
