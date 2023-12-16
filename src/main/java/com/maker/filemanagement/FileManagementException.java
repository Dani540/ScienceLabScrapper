package com.maker.filemanagement;

public class FileManagementException extends RuntimeException{
    public FileManagementException(String message) {
        super(message);
    }

    public FileManagementException(String message, int bound) {
        super(message + ", the bound is: " + bound);
    }
}
