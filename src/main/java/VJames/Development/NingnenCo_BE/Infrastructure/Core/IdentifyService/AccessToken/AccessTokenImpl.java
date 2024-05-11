package VJames.Development.NingnenCo_BE.Infrastructure.Core.IdentifyService.AccessToken;

import VJames.Development.NingnenCo_BE.Application.Authentication.Jwt.IAccessToken;
import VJames.Development.NingnenCo_BE.Domain.Entities.User;
import VJames.Development.NingnenCo_BE.Infrastructure.Core.IdentifyService.JWT.IJwtService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AccessTokenImpl implements IAccessToken {
    private final IJwtService jwtService;
    @Override
    public String generateAccessToken(User user, String refreshToken) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuer(user.getId())
                .id(refreshToken)
                .claims(
                        Map.of(
                                "appUserName", user.getAppUserName(),
                                "signature", user.getAvatar())
                )
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 7*24*60*60*1000))
                .signWith(jwtService.getSigninKey())
                .compact();
    }
}
