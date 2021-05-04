package org.ksga.springboot.springsecuritydemo.repository.provider;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostFilter {
    private Long userId;
    private String caption;
    private String username;
}
