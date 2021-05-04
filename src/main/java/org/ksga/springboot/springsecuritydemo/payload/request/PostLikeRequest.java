package org.ksga.springboot.springsecuritydemo.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostLikeRequest {
    private Long id;
    private LikeType likeType;
}

