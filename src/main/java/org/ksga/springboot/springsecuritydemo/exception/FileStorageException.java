package org.ksga.springboot.springsecuritydemo.exception;

public class FileStorageException extends RuntimeException {
    private String msg;

    public FileStorageException(String msg) {
        this.msg = msg;
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
