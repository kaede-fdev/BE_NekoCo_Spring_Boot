package VJames.Development.NingnenCo_BE.Infrastructure.Core.IdentifyService.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements IJwtService {
    public SecretKey getSigninKey() {
        String SECRET_KEY = "92f1b1366cec70d2f7cbb93f8f1ef4a42034b9b9e1233cafb026bc60cbed31c7";
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> resolvers) {
        Claims claims = extractAllClaims(token);
        return resolvers.apply(claims);
    }

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String extractRefreshToken(String token) {
        return extractClaim(token, Claims::getId);
    }

    @Override
    public String extractUserId(String token) {
        return extractClaim(token, Claims::getIssuer);
    }

    @Override
    public Boolean isValid(String token, UserDetails user) {
        return extractUserName(token).equals(user.getUsername()) && !isTokenExpired(token);
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
