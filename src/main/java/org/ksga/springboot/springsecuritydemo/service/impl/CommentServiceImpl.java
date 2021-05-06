package org.ksga.springboot.springsecuritydemo.service.impl;

import org.ksga.springboot.springsecuritydemo.model.Comment;
import org.ksga.springboot.springsecuritydemo.payload.dto.CommentDto;
import org.ksga.springboot.springsecuritydemo.payload.mapper.CommentMapper;
import org.ksga.springboot.springsecuritydemo.repository.CommentRepository;
import org.ksga.springboot.springsecuritydemo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<CommentDto> findCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findCommentsByPostId(postId);
        return commentMapper.commentsToCommentDtos(comments);
    }

    @Override
    public List<CommentDto> findRepliesByCommentId(Long commentId) {
        List<Comment> comments = commentRepository.findRepliesByCommentId(commentId);
        return commentMapper.commentsToCommentDtos(comments);
    }

    @Override
    public CommentDto findByCommentId(Long id) {
        return commentMapper.commentToCommentDto(commentRepository.findByCommentId(id));
    }

    @Override
    public boolean insertReply(CommentDto commentDto) {
        Comment comment = commentMapper.commentDtoToComment(commentDto);
        return commentRepository.insertReply(comment);
    }

    @Override
    public boolean insertComment(CommentDto commentDto) {
        Comment comment = commentMapper.commentDtoToComment(commentDto);
        return commentRepository.insertComment(comment);
    }
}
