package VJames.Development.NingnenCo_BE.Infrastructure.Core.Repositories;

import VJames.Development.NingnenCo_BE.Domain.Entities.Verify;
import VJames.Development.NingnenCo_BE.Domain.Repositories.IVerifyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VerifyRepository extends
        JpaRepository<Verify, String>,
        IVerifyRepository {
    Optional<Verify> findByUserEmail(String userEmail);
    @Modifying
    @Transactional
    @Query("UPDATE Verify v SET v.isVerify = :bool, v.verifyAt = :verifyAt where v.id = :id")
    void updateVerifyStatusById(
            @Param("id") String id,
            @Param("bool") Boolean bool,
            @Param("verifyAt") LocalDateTime verifyAt);

    @Transactional
    @Query("SELECT V FROM Verify V where  V.verifyToken = :token")
    Optional<Verify> findByVerifyToken(@Param("token") String token);

    @Modifying
    @Transactional
    @Query("UPDATE Verify  V SET V.verifyToken = :newToken, V.isVerify = :isVerify, V.expireAt = :expireAt, V.verifyAt =:verifyAt  where V.userEmail = :email")
    void updateVerifyForResendEmailByUserEmail(
            @Param("email") String email,
            @Param("newToken") String newToken,
            @Param("expireAt") LocalDateTime expireAt,
            @Param("isVerify") Boolean isVerify,
            @Param("verifyAt") LocalDateTime verifyAt);
}
