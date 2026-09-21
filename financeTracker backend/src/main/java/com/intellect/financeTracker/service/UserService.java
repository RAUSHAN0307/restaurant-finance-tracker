package com.intellect.financeTracker.service;

import com.intellect.financeTracker.model.User;

public interface UserService {
    User createUser(User user);

    User updateUser(Long userId, User user);

    void deleteUser(Long userId);

    User getUserById(Long userId);
}
