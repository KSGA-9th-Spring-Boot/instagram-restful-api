package org.ksga.springboot.springsecuritydemo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.ksga.springboot.springsecuritydemo.model.auth.User;

import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Post {
    private Long id;
    private long numberOfLikes;
    private String image;
    private String caption;
    private User owner;
    private List<User> likers;
}
