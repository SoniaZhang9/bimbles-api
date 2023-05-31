package com.bimbles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bimbles.entities.NormalUser;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface NormalUserRepository extends JpaRepository <NormalUser, Long> {

    @Query(value=
            "SELECT normal_user_id from normal_user_allergies WHERE allergy=:allergy " +
            "INTERSECT " +
            "SELECT id FROM user where province=:province " +
            "LIMIT :max"
            ,nativeQuery = true)
    List<Long> findSimilarUsersByAllergyType(Integer allergy, Integer max, String province);

    @Query(value=
            "SELECT normal_user_id from normal_user_diets WHERE diet=:diet " +
            "INTERSECT " +
            "SELECT id FROM user where province=:province " +
            "LIMIT :max"
            , nativeQuery = true)
    List<Long> findSimilarUsersByDietType(Integer diet, Integer max, String province);

    @Query(value=
            "SELECT normal_user_id from normal_user_special_needs WHERE special_need=:special_need " +
            "INTERSECT " +
            "SELECT id FROM user where province=:province " +
            "LIMIT :max"
            , nativeQuery = true)
    List<Long> findSimilarUsersBySpecialNeedType(Integer special_need, Integer max, String province);

}
