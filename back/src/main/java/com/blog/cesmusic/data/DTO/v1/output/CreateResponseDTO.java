package com.blog.cesmusic.data.DTO.v1.output;

public class CreateResponseDTO <T> {
    private T id;
    private String message;

    public CreateResponseDTO(T id, String message) {
        this.id = id;
        this.message = message;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
