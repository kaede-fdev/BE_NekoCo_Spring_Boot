package VJames.Development.NingnenCo_BE.Infrastructure.Core.IdentifyService.JWT;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

public interface IJwtService {
    Claims extractAllClaims(String token);
    <T> T extractClaim(String token, Function<Claims, T> resolvers);
    String extractUserName(String token);
    String extractRefreshToken(String token);
    String extractUserId(String token);
    Boolean isValid(String token, UserDetails user);
    Boolean isTokenExpired(String token);
    Date extractExpiration(String token);
    SecretKey getSigninKey();
}
