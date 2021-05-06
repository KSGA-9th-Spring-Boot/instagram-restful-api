package org.ksga.springboot.springsecuritydemo.payload.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class UserDto {
    private Long id;
    private String fullname;
    private String username;
    private String imageUrl;
}
