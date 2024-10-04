package com.mario.faceengine.repository;

import com.mario.faceengine.entity.FaceImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaceImageRepository extends JpaRepository<FaceImage, Long> { }
