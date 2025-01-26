package org.example.universitytodolist.mapper;

import org.example.universitytodolist.DTOs.CommentDTO;
import org.example.universitytodolist.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentDTO toDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setTaskId(comment.getTask().getId());
        commentDTO.setUserId(comment.getUser().getId());

        return commentDTO;
    }
}
