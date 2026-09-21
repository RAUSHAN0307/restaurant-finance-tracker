package com.intellect.financeTracker.repository;

import com.intellect.financeTracker.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByIsReadFalse();

    List<Message> findByType(String type);

    void deleteByReferenceIdAndReferenceType(Long referenceId, String referenceType);

    List<Message> findByUserId(Long userId);

    List<Message> findByUserIdAndIsReadFalse(Long userId);

    List<Message> findByUserIdAndType(Long userId, String type);
}
