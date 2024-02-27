package com.basic.sftp.controller;

import com.basic.sftp.models.request.CreateDirectoryModel;
import com.basic.sftp.models.request.DeleteFileOrDirectoryModel;
import com.basic.sftp.service.CreateDirectoryService;
import com.basic.sftp.service.DeleteFileOrDirectoryService;
import com.basic.sftp.service.UploadFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/sftp-management/v1")
@AllArgsConstructor
public class sftpManagementController {

    private final CreateDirectoryService createDirectoryService;
    private final UploadFileService uploadFileService;
    private final DeleteFileOrDirectoryService deleteFileOrDirectoryService;

    @PostMapping("/create/directory")
    public ResponseEntity<String> createDirectory(@RequestBody CreateDirectoryModel req) {
        return createDirectoryService.createDirectory(req);
    }

    @PostMapping("/upload/file")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") String userId
    ) {
        return uploadFileService.uploadFile(file, userId);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteFileOrDirectory(@RequestBody DeleteFileOrDirectoryModel req) {
        return deleteFileOrDirectoryService.deleteFileOrDirectory(req);
    }

}
