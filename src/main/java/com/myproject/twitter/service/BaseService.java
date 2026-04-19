package com.myproject.twitter.service;

import com.myproject.twitter.entity.User;
import com.myproject.twitter.exception.TwitterForbiddenException;
import com.myproject.twitter.exception.TwitterNotFoundException;
import com.myproject.twitter.repository.UserRepository;
import com.myproject.twitter.util.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService {

    @Autowired
    protected UserRepository userRepository;

    protected User getCurrentUser() {
        String email = SecurityUtils.getCurrentUserEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new TwitterNotFoundException("Giriş yapmış kullanıcı bulunamadı."));
    }

    protected void verifyOwnership(String resourceOwnerEmail) {
        User currentUser = getCurrentUser();
        if (!currentUser.getEmail().equals(resourceOwnerEmail)) {
            throw new TwitterForbiddenException("Bu işlem için yetkiniz yok!");
        }
    }

    protected void verifyCommentDeletionAuth(Long commentOwnerId, Long tweetOwnerId) {
        User currentUser = getCurrentUser();
        Long currentId = currentUser.getId();

        if (!currentId.equals(commentOwnerId) && !currentId.equals(tweetOwnerId)) {
            throw new TwitterForbiddenException("Yorumu silme yetkiniz yok!");
        }
    }
}
