package com.gstagram.gstagram.like.application;

import com.gstagram.gstagram.like.domain.Like;
import com.gstagram.gstagram.user.domain.User;

import java.util.List;

public interface LikeService {
    void like(String userId, Long courseId);
    void unlike(String userId, Long courseId);
    boolean isLiked(String userId, Long courseId);
    int getLikeCount(Long courseId);

    List<User> getLikeUserList(Long courseId);
}
