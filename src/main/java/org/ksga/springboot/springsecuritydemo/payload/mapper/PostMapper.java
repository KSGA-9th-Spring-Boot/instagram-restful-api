package org.ksga.springboot.springsecuritydemo.payload.mapper;

import org.ksga.springboot.springsecuritydemo.model.Post;
import org.ksga.springboot.springsecuritydemo.model.auth.User;
import org.ksga.springboot.springsecuritydemo.payload.dto.PostDto;
import org.ksga.springboot.springsecuritydemo.security.service.UserDetailsImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mappings({
            @Mapping(source = "owner.id", target = "userId"),
            @Mapping(source = "owner.username", target = "username"),
            @Mapping(target = "postDto.isOwner", expression = "java(isOwner(owner))")
    })
    PostDto postToPostDto(Post post);

    @Mappings({
            @Mapping(target = "owner.id", source = "userId"),
            @Mapping(target = "owner.username", source = "username"),
    })
    Post postDtoToPost(PostDto post);

    List<PostDto> postsToPostDtos(List<Post> posts);

    default boolean isOwner(User user) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        System.out.println(principal);
        return user.getUsername().equals(username);
    }

}
