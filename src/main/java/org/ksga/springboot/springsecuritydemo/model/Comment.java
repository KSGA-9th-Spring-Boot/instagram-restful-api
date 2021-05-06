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
public class Comment {
    private Long id;
    private String content;
    private User user;
    private Post post;
    private Long parentId;
    private List<Comment> comments;
}
