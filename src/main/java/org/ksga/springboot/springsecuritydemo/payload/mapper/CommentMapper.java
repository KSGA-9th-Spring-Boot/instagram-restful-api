package org.ksga.springboot.springsecuritydemo.payload.mapper;

import org.ksga.springboot.springsecuritydemo.model.Comment;
import org.ksga.springboot.springsecuritydemo.payload.dto.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mappings({
            @Mapping(source = "user", target = "user"),
            @Mapping(source = "post.id", target = "postId"),
    })
    CommentDto commentToCommentDto(Comment comment);

    @Mappings({
            @Mapping(source = "user", target = "user"),
            @Mapping(source = "postId", target = "post.id"),
    })
    Comment commentDtoToComment(CommentDto commentDto);

    List<CommentDto> commentsToCommentDtos(List<Comment> comments);

}
