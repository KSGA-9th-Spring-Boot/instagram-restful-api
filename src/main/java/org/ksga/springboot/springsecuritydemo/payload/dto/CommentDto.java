package org.ksga.springboot.springsecuritydemo.payload.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CommentDto {
    private Long id;
    private String content;
    private UserDto user;
    private Long postId;
    private Long parentId;
    private List<CommentDto> comments;
}
