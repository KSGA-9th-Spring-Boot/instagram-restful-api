package org.ksga.springboot.springsecuritydemo.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CommentRequest {
    private String content;
    private Long parentId;
    private Long userId;
    private Long postId;
}
