package com.nawid.expense_tracking_system.domain.repository;

import com.nawid.expense_tracking_system.domain.enums.Role;
import com.nawid.expense_tracking_system.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Boolean existsByRole(Role role);
    Boolean existsByEmail(String email);
    User save(User user);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    Optional<User> findByRole(Role role);
    void deleteById(Long id);

    Optional<User> findById(long id);



}
