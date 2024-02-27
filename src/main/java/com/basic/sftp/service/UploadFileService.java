package com.basic.sftp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UploadFileService {

    public ResponseEntity<String> uploadFile() {
        return ResponseEntity.ok("");
    }
}
