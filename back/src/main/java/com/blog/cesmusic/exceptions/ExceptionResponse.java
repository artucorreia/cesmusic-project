package com.blog.cesmusic.exceptions;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ExceptionResponse implements Serializable {
    private LocalDateTime timestamp;
    private String title;
    private String details;

    public ExceptionResponse(LocalDateTime timestamp, String title, String details) {
        this.timestamp = timestamp;
        this.title = title;
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return details;
    }
}
