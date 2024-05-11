package VJames.Development.NingnenCo_BE.Infrastructure.Core.Repositories;

import VJames.Development.NingnenCo_BE.Domain.Entities.Follow;
import VJames.Development.NingnenCo_BE.Domain.Repositories.IFollowRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FollowingRepository extends
        JpaRepository<Follow, String>,
        IFollowRepository {
    @Transactional
    @Query("SELECT F FROM Follow F WHERE F.userId = :fromUser AND F.followingId = :toUser ORDER BY F.userId LIMIT  1")
    Follow findByUserIdAndFollowingId(@Param("fromUser") String fromUser,@Param("toUser") String toUser);

    @Transactional
    @Query("SELECT U.id as id, U.appUserName as appUserName, U.avatar as avatar, U.firstName as firstName, U" +
            ".lastName as lastName, F.followAt as followAt  FROM User U JOIN Follow F ON U.id = F.followingId " +
            "WHERE F.userId = :userId ORDER BY F.followAt DESC")
    List<Object[]> findAllUserFollowingFromUserId(@Param("userId") String userId);

    @Transactional
    @Query("SELECT U.id as id, U.appUserName as appUserName, U.avatar as avatar, U.firstName as firstName, U." +
            "lastName as lastName, F.followAt as followYouAt FROM User U JOIN Follow  F ON U.id = F.userId WHERE " +
            "F.followingId = :userFollowed ORDER BY F.followAt DESC")
    List<Object[]> findAllFollowsToUser(@Param("userFollowed") String userFollowed);

    @Transactional
    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END AS isFollowed FROM Follow F WHERE F.userId = :userId " +
            "AND F.followingId =:followingId ORDER BY F.followAt DESC")
    Boolean checkIsFollowed(@Param("userId") String userId, @Param("followingId") String followingId);

    @Transactional
    @Query(value = "SELECT DISTINCT id, app_user_name, first_name, last_name, avatar, status, disconnect_at " +
            "FROM (" +
            "         SELECT U.id, U.app_user_name, U.avatar, U.first_name, U.last_name, U.status, U.disconnect_at " +
            "         FROM User U " +
            "                  JOIN Follow F ON U.id = F.user_id " +
            "         WHERE F.following_id = :userId " +
            "         UNION " +
            "         SELECT U.id, U.app_user_name, U.avatar, U.first_name, U.last_name, U.status, U.disconnect_at " +
            "         FROM User U " +
            "                  JOIN Follow F ON U.id = F.following_id " +
            "         WHERE F.user_id = :userId " +
            ") AS subquery", nativeQuery = true)
    List<Object[]> finalAllUserCanChatWith(@Param("userId") String userId);
}
