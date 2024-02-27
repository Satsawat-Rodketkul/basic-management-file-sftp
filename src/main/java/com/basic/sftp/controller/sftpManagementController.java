package com.basic.sftp.controller;

import com.basic.sftp.models.request.CreateDirectoryModel;
import com.basic.sftp.service.CreateDirectoryService;
import com.basic.sftp.service.UploadFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sftp-management/v1")
@AllArgsConstructor
public class sftpManagementController {

    private final CreateDirectoryService createDirectoryService;
    private final UploadFileService uploadFileService;

    @PostMapping("/create/directory")
    public ResponseEntity<String> createDirectory(@RequestBody CreateDirectoryModel req) {
        return createDirectoryService.createDirectory(req);
    }

    @PostMapping("/upload/file")
    public ResponseEntity<String> uploadFile() {
        return uploadFileService.uploadFile();
    }

}
