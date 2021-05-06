package org.ksga.springboot.springsecuritydemo.service;

import org.ksga.springboot.springsecuritydemo.payload.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> findCommentsByPostId(Long postId);
    List<CommentDto> findRepliesByCommentId(Long commentId);

    CommentDto findByCommentId(Long id);

    boolean insertReply(CommentDto comment);
    boolean insertComment(CommentDto comment);
}
