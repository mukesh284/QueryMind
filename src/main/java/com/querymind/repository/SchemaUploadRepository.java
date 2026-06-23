package com.querymind.repository;

import com.querymind.entity.SchemaUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

/**
 * Repository for SchemaUpload entity
 */
@Repository
public interface SchemaUploadRepository extends JpaRepository<SchemaUpload, Long> {
    Optional<SchemaUpload> findByFileName(String fileName);
    List<SchemaUpload> findAllByOrderByUploadedAtDesc();
}

