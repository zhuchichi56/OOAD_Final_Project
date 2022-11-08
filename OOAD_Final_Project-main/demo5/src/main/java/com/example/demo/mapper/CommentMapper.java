package com.example.demo.mapper;

import com.example.demo.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    int insertComment(@Param("commentId") String hash,@Param("comment") String comment,
                      @Param("agent") String agent, @Param("issueId") String issueId, @Param("time") String time);

    List<Comment> getComments(@Param("issueId") String issueId);

    int deleteComment(@Param("commentId") String hash);

    int updateCommentUserName(@Param("oldName") String oldName, @Param("newName") String newName);
}
