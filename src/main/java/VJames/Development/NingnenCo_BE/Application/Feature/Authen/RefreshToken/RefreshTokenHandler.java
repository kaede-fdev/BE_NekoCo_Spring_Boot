package VJames.Development.NingnenCo_BE.Application.Feature.Authen.RefreshToken;

import VJames.Development.NingnenCo_BE.Application.Authentication.Jwt.IAccessToken;
import VJames.Development.NingnenCo_BE.Application.Authentication.Jwt.IRefreshToken;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.RefreshToken.Middleware.IRefreshTokenMiddleware;
import VJames.Development.NingnenCo_BE.Domain.Entities.Token;
import VJames.Development.NingnenCo_BE.Domain.UnitOfWork.IUnitOfWork;
import VJames.Development.NingnenCo_BE.Infrastructure.Core.IdentifyService.JWT.IJwtService;
import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class RefreshTokenHandler implements Command.Handler<RefreshTokenRequest, RefreshTokenResponse>, IRefreshTokenMiddleware {
    @Autowired
    private final IUnitOfWork _unitOfWork;
    private final IJwtService _jwt;
    private final IAccessToken _accessFactory;
    private final IRefreshToken _refreshFactory;

    @Override
    public RefreshTokenResponse handle(RefreshTokenRequest request) {
        String refreshTokenFromHeader = _jwt.extractRefreshToken(request.getAccessToken().substring(7));
        //todo caching
        //...
        Token existedToken = _unitOfWork.getTokenRepository().findByRefreshToken(request.getRefreshToken());
        if(existedToken != null) {
            if(!existedToken.getRefreshTokenExpirationTime().isBefore(LocalDateTime.now())) {
                try {
                    if(refreshTokenFromHeader.equals(existedToken.getRefreshToken())) {
                        AtomicReference<String> newRefreshToken = new AtomicReference<>();
                        AtomicReference<String> newAccessToken = new AtomicReference<>();
                        _unitOfWork.getUserRepository().findById(existedToken.getUserId()).ifPresent(user -> {
                            newRefreshToken.set(_refreshFactory.generateRefreshToken());
                            newAccessToken.set(_accessFactory.generateAccessToken(user, newRefreshToken.get()));
                            //todo update cache
                            //...
                            _unitOfWork.getTokenRepository().updateTokensById(
                                    newRefreshToken.get(),
                                    LocalDateTime.now().plusDays(14), existedToken.getId());
                        });
                        return RefreshTokenResponse.builder()
                                .statusCode(RefreshTokenResponseStatusCode.REFRESH_SUCCESS)
                                .httpStatus(HttpStatus.OK)
                                .accessToken(newAccessToken.get())
                                .refreshToken(newRefreshToken.get())
                                .build();
                    }
                    return RefreshTokenResponse.builder()
                            .statusCode(RefreshTokenResponseStatusCode.REFRESH_TOKEN_NOT_MATCH_INNER_AUTH)
                            .httpStatus(HttpStatus.EXPECTATION_FAILED)
                            .build();
                } catch (Exception ex) {
                    return RefreshTokenResponse.builder()
                            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(RefreshTokenResponseStatusCode.REFRESH_ACCESS_TOKEN_ERROR)
                            .build();
                }
            }
            return RefreshTokenResponse.builder()
                    .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                    .statusCode(RefreshTokenResponseStatusCode.REFRESH_TOKEN_EXPIRED)
                    .build();

        }
        return RefreshTokenResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .statusCode(RefreshTokenResponseStatusCode.NOT_FOUND_REFRESH_TOKEN)
                .build();

    }
}
