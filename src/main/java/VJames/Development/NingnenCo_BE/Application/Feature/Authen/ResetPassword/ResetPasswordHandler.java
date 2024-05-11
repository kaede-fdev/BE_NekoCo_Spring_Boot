package VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResetPassword;

import VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResetPassword.Middleware.IResetPasswordMiddleware;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResetPassword.Middleware.ResetPasswordValidationMiddleware;
import VJames.Development.NingnenCo_BE.Application.UseCase.Base.IUseCase;
import VJames.Development.NingnenCo_BE.Domain.Dtos.UserDto;
import VJames.Development.NingnenCo_BE.Domain.Entities.ResetPasswordToken;
import VJames.Development.NingnenCo_BE.Domain.UnitOfWork.IUnitOfWork;
import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class ResetPasswordHandler implements
        Command.Handler<ResetPasswordRequest, ResetPasswordResponse>,
        IResetPasswordMiddleware {
    @Autowired
    private final ResetPasswordValidationMiddleware middleware;
    @Autowired
    private final IUseCase _useCase;
    @Autowired
    private final IUnitOfWork _unitOfWork;
    @Override
    public ResetPasswordResponse handle(ResetPasswordRequest request) {
        Pipeline pipeline = new Pipelinr().with(() -> Stream.of(middleware));
        var middlewareRes = pipeline.send(request);
        if(middlewareRes != null) return middlewareRes;
        //checking user
        UserDto foundUser = _useCase.getAuthUseCase().findExistedUser(request.getUserName());
        if(foundUser == null)
            return ResetPasswordResponse.builder()
                    .statusCode(ResetPasswordResponseStatusCode.USER_NOT_FOUND)
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        ResetPasswordToken resetPasswordToken = _unitOfWork.getResetPasswordTokenRepository()
                    .findByResetCode(request.getPassCode());
        if(resetPasswordToken == null) {
            return ResetPasswordResponse.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .statusCode(ResetPasswordResponseStatusCode.PASSCODE_NOT_FOUND)
                    .build();
        }
        if(resetPasswordToken.getExpireAt().isBefore(LocalDateTime.now())) {
            return ResetPasswordResponse.builder()
                    .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                    .statusCode(ResetPasswordResponseStatusCode.PASSCODE_EXPIRED)
                    .build();
        }
        _useCase.getTokenUseCase().getPassCodeAndUpdatingUserPassword(request.getConfirmNewPassword() ,resetPasswordToken);
        return ResetPasswordResponse.builder()
                .statusCode(ResetPasswordResponseStatusCode.RESET_PASSWORD_SUCCESS)
                .httpStatus(HttpStatus.OK)
                .build();
    }

}
