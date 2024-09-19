package com.raveesha.request;

import lombok.Data;

@Data
public class MessageDto {
    private String content;
    private Long projectId;
    private Long senderId;
}
