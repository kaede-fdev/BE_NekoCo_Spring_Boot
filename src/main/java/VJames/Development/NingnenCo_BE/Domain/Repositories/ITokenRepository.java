package VJames.Development.NingnenCo_BE.Domain.Repositories;

import VJames.Development.NingnenCo_BE.Domain.Entities.Token;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ITokenRepository {
    List<Token> findAllByUserId(String userId);
    Token findByRefreshToken(String refreshToken);
    Token findEarliestExpiredTokenByUserId(@Param("userId") String userId);
    void deleteByRefreshToken(String token);
    void updateTokensById(String newRefreshToken, LocalDateTime newRefreshTokenExpiration, String tokenId);
    Token save(Token bearer);
}
