package VJames.Development.NingnenCo_BE.Infrastructure.Core.Repositories;

import VJames.Development.NingnenCo_BE.Domain.Repositories.IUserRepository;
import VJames.Development.NingnenCo_BE.Domain.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends
        JpaRepository<User, String>,
        IUserRepository {
    @Transactional
    @Query("SELECT U FROM User U WHERE U.username = :username ORDER BY U.id LIMIT 1")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.id = :userId" )
    Optional<User> findById(@Param("userId") String userId);
    @Query("SELECT u FROM User u WHERE u.id = :userId" )
    Optional<User> findByIdOptional(String userId);
    @Modifying
    @Transactional
    @Query("UPDATE User U SET U.password = :newPassword WHERE U.id = :userId")
    void updatePassword(@Param("newPassword") String newPassword, @Param("userId") String userId);
//    @Modifying
//    @Transactional
//    @Query("UPDATE User U SET U.status = :status WHERE U.username = :username")
//    void updateStatus(String status, String username);
//    @Modifying
//    @Transactional
//    @Query("UPDATE User U SET U.disconnectAt = :time WHERE U.username = :username")
//    void updateDisconnectTime(LocalDateTime time, String username);
//    @Transactional
//    @Query("SELECT U FROM User U WHERE U.id != :userId AND U.status = :status")
//    List<User> findAllByStatus(@Param("userId") String userId, @Param("status") String status);
//
    @Transactional
    @Query("SELECT U FROM User U WHERE  U.id != :userId AND U.id NOT IN (SELECT F.followingId FROM Follow F WHERE F.userId = :userId)" +
            "ORDER BY Rand() LIMIT 10")
    List<User> findAllIfNotFollowingByCurrenUserId(@Param("userId") String userId);
//
//    @Modifying
//    @Transactional
//    @Query("UPDATE User U SET U.avatar = :avatarUrl WHERE U.id = :userId")
//    void updateUserAvatarByUserId(@Param("userId") String userId, @Param("avatarUrl") String avatarUrl);
}
