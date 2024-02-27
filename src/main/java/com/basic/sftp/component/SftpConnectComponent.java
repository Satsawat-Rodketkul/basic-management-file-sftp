package com.basic.sftp.component;

import com.basic.sftp.models.SftpConnectionModel;
import com.basic.sftp.property.SftpConnectionProperty;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SftpConnectComponent {

    private final SftpConnectionProperty sftpConnectionProperty;

    public SftpConnectionModel connectSftp() throws JSchException {
        SftpConnectionModel sftpConnection = new SftpConnectionModel();
        Session session;
        ChannelSftp channel;

        JSch jsch = new JSch();
        session = jsch.getSession(
                sftpConnectionProperty.getUsername(),
                sftpConnectionProperty.getHost(),
                sftpConnectionProperty.getPort()
        );
        session.setPassword(sftpConnectionProperty.getPassword());
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        channel = (ChannelSftp) session.openChannel(sftpConnectionProperty.getType());
        channel.connect();

        sftpConnection.setSession(session);
        sftpConnection.setChannel(channel);

        return sftpConnection;
    }
}