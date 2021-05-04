package org.ksga.springboot.springsecuritydemo.payload.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostDto {
    private Long id;
    private String caption;
    private String image;
    private Long numberOfLikes;
    private Long userId;
    private String username;
    private boolean isOwner;
}
