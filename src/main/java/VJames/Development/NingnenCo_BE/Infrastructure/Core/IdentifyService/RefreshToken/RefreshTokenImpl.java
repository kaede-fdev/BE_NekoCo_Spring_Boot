package VJames.Development.NingnenCo_BE.Infrastructure.Core.IdentifyService.RefreshToken;

import VJames.Development.NingnenCo_BE.Application.Authentication.Jwt.IRefreshToken;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class RefreshTokenImpl implements IRefreshToken {
    @Override
    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }
}
