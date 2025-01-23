package com.penta.security.user.repository;

import com.penta.security.entity.SystemUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 회원 레포지토리
 */
public interface SystemUserRepository extends JpaRepository<SystemUser, Integer>, SystemUserCustomRepository {

    Optional<SystemUser> findByUserId(String userId);

    @Query(
        value = "SELECT * FROM system_user " +
            "WHERE (:searchWord IS NULL OR :searchWord = '' OR user_id LIKE CONCAT(:searchWord, '%') " +
            "OR user_nm LIKE CONCAT(:searchWord, '%')) " +
            "ORDER BY user_idx DESC LIMIT :pageSize",
        nativeQuery = true
    )
    List<SystemUser> findFirstPageBySearchWord(
        @Param("searchWord") String searchWord,
        @Param("pageSize") int pageSize
    );

    @Query(
        value = "SELECT * FROM system_user " +
            "WHERE (:searchWord IS NULL OR :searchWord = '' OR user_id LIKE CONCAT(:searchWord, '%') " +
            "OR user_nm LIKE CONCAT(:searchWord, '%')) " +
            "AND user_idx < :lastUserIdx " +
            "ORDER BY user_idx DESC LIMIT :pageSize",
        nativeQuery = true
    )
    List<SystemUser> findPageBySearchWordWithLastUserIdx(
        @Param("searchWord") String searchWord,
        @Param("lastUserIdx") Integer lastUserIdx,
        @Param("pageSize") int pageSize
    );

    @Query(
        value = "SELECT COUNT(*) FROM system_user " +
            "WHERE (:searchWord IS NULL OR :searchWord = '' OR user_id LIKE CONCAT(:searchWord, '%') " +
            "OR user_nm LIKE CONCAT(:searchWord, '%'))",
        nativeQuery = true
    )
    int countUsersBySearchWord(@Param("searchWord") String searchWord);
}

