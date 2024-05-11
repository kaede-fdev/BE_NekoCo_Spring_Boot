package VJames.Development.NingnenCo_BE.Application.Feature.Authen.SendResetPasswordCode;

import VJames.Development.NingnenCo_BE.Application.Feature.Authen.SendResetPasswordCode.Middleware.ISendResetPasswordCode;
import VJames.Development.NingnenCo_BE.Application.UseCase.Base.IUseCase;
import VJames.Development.NingnenCo_BE.Domain.Dtos.UserDto;
import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendResetPasswordCodeHandler implements
        Command.Handler<SendResetPasswordCodeRequest, SendResetPasswordCodeResponse>,
        ISendResetPasswordCode {
    private final IUseCase _useCase;
    @Override
    public SendResetPasswordCodeResponse handle(SendResetPasswordCodeRequest request) {
        UserDto foundUser = _useCase.getAuthUseCase().findExistedUser(request.getEmail());
        if(foundUser == null) {
            return SendResetPasswordCodeResponse.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .statusCode(SendResetPasswordCodeStatusCode.DOESNT_EXISTED_IN_SYSTEM)
                    .build();
        }
        if(!_useCase.getVerifyUseCase().isAccountVerified(request.getEmail())) {
            return SendResetPasswordCodeResponse.builder()
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .statusCode(SendResetPasswordCodeStatusCode.NOT_YET_VERIFICATION)
                    .build();
        }
        _useCase.getTokenUseCase().createAndSendResetPassCodeToUserWithId(foundUser.getId(), foundUser.getEmail());
        return SendResetPasswordCodeResponse.builder()
                .statusCode(SendResetPasswordCodeStatusCode.SEND_RESET_PASSWORD_EMAIL_SUCCESS)
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
