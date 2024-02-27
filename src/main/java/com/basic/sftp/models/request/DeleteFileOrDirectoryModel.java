package com.basic.sftp.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteFileOrDirectoryModel {
    private String userId;
    private String type;
    private String fileOrDirectoryName;
}
