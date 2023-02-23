package com.bimbles.repositories;

import com.bimbles.utils.types.AllergyType;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bimbles.entities.NormalUser;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NormalUserRepository extends JpaRepository <NormalUser, Long> {

    @Query(value= "SELECT normal_user_id from normal_user_allergies WHERE allergy=:allergy LIMIT :max", nativeQuery = true)
    List<Long> findSimilarUsersByAllergyType(Integer allergy, Integer max);

    @Query(value= "SELECT normal_user_id from normal_user_diets WHERE diet=:diet LIMIT :max", nativeQuery = true)
    List<Long> findSimilarUsersByDietType(Integer diet, Integer max);

    @Query(value= "SELECT normal_user_id from normal_user_special_needs WHERE special_need=:special_need LIMIT :max", nativeQuery = true)
    List<Long> findSimilarUsersBySpecialNeedType(Integer special_need, Integer max);

}
