package com.nawid.expense_tracking_system.domain.repository;

import com.nawid.expense_tracking_system.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Boolean existsByEmail(String email);
    User save(User user);
    Optional<User> findByEmail(String email);

}
