package com.intellect.financeTracker.repository;

import com.intellect.financeTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByUserName(String userName);

    User findByEmpId(Long empId);

    @Query("SELECT e.user.userId FROM Employee e WHERE e.email = :email AND e.role = 'MANAGER'")
    java.util.List<Long> findAdminIdsByEmail(@Param("email") String email);

    @Query("SELECT e.user.userId FROM Employee e WHERE e.email = :email AND e.role = 'WAITER'")
    java.util.List<Long> findWaiterIdsByEmail(@Param("email") String email);
}
