package VJames.Development.NingnenCo_BE.Domain.Repositories;

import VJames.Development.NingnenCo_BE.Domain.Entities.Verify;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface IVerifyRepository {
    Optional<Verify> findByUserEmail(String userEmail);

    void updateVerifyStatusById(String id, Boolean bool, LocalDateTime verifyAt);

    Optional<Verify> findByVerifyToken(@Param("token") String token);

    void updateVerifyForResendEmailByUserEmail(
            String email,
            String newToken,
            LocalDateTime expireAt,
            Boolean isVerify,
            LocalDateTime verifyAt);

    Verify save(Verify verify);
//    void save(Verify verify);
}
