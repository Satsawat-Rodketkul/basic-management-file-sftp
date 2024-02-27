package com.basic.sftp.service;

import com.basic.sftp.component.SftpConnectComponent;
import com.basic.sftp.models.SftpConnectionModel;
import com.basic.sftp.models.request.DeleteFileOrDirectoryModel;
import com.basic.sftp.property.SftpPathProperty;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Vector;

@Service
@Slf4j
@AllArgsConstructor
public class DeleteFileOrDirectoryService {

    private final SftpConnectComponent sftpConnectComponent;
    private final SftpPathProperty sftpPathProperty;

    public ResponseEntity<String> deleteFileOrDirectory(DeleteFileOrDirectoryModel req) {
        Session session = null;
        ChannelSftp channelSftp = null;
        String targetPath;
        try {
            //Connect SFTP server and set to variable
            SftpConnectionModel sftpConnection = sftpConnectComponent.connectSftp();
            session = sftpConnection.getSession();
            channelSftp = sftpConnection.getChannel();

            //Create target path for delete
            switch (req.getType()) {
                case "file":
                    targetPath = sftpPathProperty.getPath() + req.getUserId() + "/" + req.getFileOrDirectoryName();
                    channelSftp.rm(targetPath);
                    break;
                case "directory":
                    targetPath = sftpPathProperty.getPath() + req.getUserId();
                    removeDirectory(channelSftp, targetPath);
                    break;
                default:
                    throw new RuntimeException("Type not found");
            }
            log.info("Delete {} success", req.getType());
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
        return ResponseEntity.ok("Delete file or directory success");
    }

    private void removeDirectory(ChannelSftp channelSftp, String targetPath) throws SftpException {
        Vector<ChannelSftp.LsEntry> fileList = channelSftp.ls(targetPath);
        for (ChannelSftp.LsEntry entry : fileList) {
            String filename = entry.getFilename();
            if (!filename.equals(".") && !filename.equals("..")) {
                String filePath = targetPath + "/" + entry.getFilename();
                if (entry.getAttrs().isDir()) {
                    //Remove subdirectory
                    channelSftp.rmdir(targetPath);
                } else {
                    channelSftp.rm(filePath);
                }
            }
        }
        channelSftp.rmdir(targetPath);
    }
}