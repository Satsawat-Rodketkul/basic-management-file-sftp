package com.basic.sftp.service;

import com.basic.sftp.component.SftpConnectComponent;
import com.basic.sftp.models.SftpConnectionModel;
import com.basic.sftp.models.request.CreateDirectoryModel;
import com.basic.sftp.property.SftpPathProperty;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CreateDirectoryService {

    private final SftpConnectComponent sftpConnectComponent;
    private final SftpPathProperty sftpPathProperty;

    public ResponseEntity<String> createDirectory(CreateDirectoryModel req) {
        Session session = null;
        ChannelSftp channelSftp = null;
        try {
            String newDirectory = sftpPathProperty.getPath() + req.getUserId();

            //Connect SFTP server and set to variable
            SftpConnectionModel sftpConnection = sftpConnectComponent.connectSftp();
            session = sftpConnection.getSession();
            channelSftp = sftpConnection.getChannel();

            String[] folders = newDirectory.split("/");
            for (String folder : folders) {
                if (!folder.isEmpty()) {
                    try {
                        channelSftp.cd(folder);
                    } catch (SftpException e) {
                        channelSftp.mkdir(folder);
                        channelSftp.cd(folder);
                    }
                }
            }
            log.info("Create directory id:{} success", req.getUserId());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (session != null) {
                log.info("disconnect session");
                session.disconnect();
            }
            if (channelSftp != null) {
                log.info("disconnect channelSftp");
                channelSftp.disconnect();
            }
        }
        return ResponseEntity.ok("Create directory success");
    }
}