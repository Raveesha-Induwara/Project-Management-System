package com.raveesha.request;

import lombok.Data;

@Data
public class CommentRequestDto {
    private String content;
    private Long issueId;
}
