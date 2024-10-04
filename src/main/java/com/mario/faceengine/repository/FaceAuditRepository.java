package com.mario.faceengine.repository;

import com.mario.faceengine.entity.FaceAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaceAuditRepository extends JpaRepository<FaceAudit, Long> { }
