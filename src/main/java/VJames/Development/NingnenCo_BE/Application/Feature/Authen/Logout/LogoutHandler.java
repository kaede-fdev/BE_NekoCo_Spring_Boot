package VJames.Development.NingnenCo_BE.Application.Feature.Authen.Logout;

import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Logout.Middleware.ILogoutMiddleware;
import VJames.Development.NingnenCo_BE.Domain.UnitOfWork.IUnitOfWork;
import VJames.Development.NingnenCo_BE.Infrastructure.Core.IdentifyService.JWT.IJwtService;
import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutHandler implements Command.Handler<LogoutRequest, LogoutResponse>, ILogoutMiddleware {
    @Autowired
    private final IUnitOfWork _unitOfWork;
    @Autowired
    private final IJwtService _jwt;
    @Override
    public LogoutResponse handle(LogoutRequest request) {
        String rToken = _jwt.extractRefreshToken(request.getAuthorizationToken());
        _unitOfWork.getTokenRepository().deleteByRefreshToken(rToken);
        return LogoutResponse.builder()
                .httpStatus(HttpStatus.OK)
                .statusCode(LogoutResponseStatusCode.LOGOUT_SUCCESS)
                .build();
    }
}
