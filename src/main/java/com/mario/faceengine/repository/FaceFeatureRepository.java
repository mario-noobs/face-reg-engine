package com.mario.faceengine.repository;

import com.mario.faceengine.entity.FaceFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaceFeatureRepository extends JpaRepository<FaceFeature, Integer> {
    // Additional query methods if needed
    FaceFeature findTopByUserIdOrderByCreateDateDesc(String userId);
}
