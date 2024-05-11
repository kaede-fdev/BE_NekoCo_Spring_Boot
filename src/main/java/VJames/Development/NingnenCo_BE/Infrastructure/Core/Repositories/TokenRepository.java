package VJames.Development.NingnenCo_BE.Infrastructure.Core.Repositories;

import VJames.Development.NingnenCo_BE.Domain.Entities.Token;
import VJames.Development.NingnenCo_BE.Domain.Repositories.ITokenRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TokenRepository extends
        JpaRepository<Token, String>,
        ITokenRepository {
    @Override
    List<Token> findAllByUserId(String userId);

    @Override
    Token findByRefreshToken(String refreshToken);

    @Override
    @Transactional
    @Query("SELECT T FROM Token T WHERE T.userId = :userId ORDER BY T.refreshTokenExpirationTime LIMIT 1")
    Token findEarliestExpiredTokenByUserId(@Param("userId") String userId);

    @Override
    @Modifying
    @Transactional
    void deleteByRefreshToken(String token);


    @Modifying
    @Transactional
    @Query("UPDATE Token t SET  t.refreshToken = :newRefreshToken, t.refreshTokenExpirationTime = " +
            ":newRefreshTokenExpiration"+
            " WHERE t.id = " +
            ":tokenId")
    void updateTokensById(
                          @Param("newRefreshToken") String newRefreshToken,
                          @Param("newRefreshTokenExpiration") LocalDateTime newRefreshTokenExpiration,
                          @Param("tokenId") String tokenId);
}
