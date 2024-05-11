package VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResetPassword.Middleware;

import VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResetPassword.ResetPasswordRequest;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResetPassword.ResetPasswordResponse;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResetPassword.ResetPasswordResponseStatusCode;
import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResetPasswordValidationMiddleware implements
        Command.Handler<ResetPasswordRequest, ResetPasswordResponse>,
        IResetPasswordMiddleware {
    @Override
    public ResetPasswordResponse handle(ResetPasswordRequest request) {
        ResetPasswordValidation validation = new ResetPasswordValidation();
        if(validation.isEmptyRequest(request)) {
            return ResetPasswordResponse.builder()
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .statusCode(ResetPasswordResponseStatusCode.INPUT_EMPTY_VALUE)
                    .build();
        }
        if(!validation.isValidEmailFormat(request)) {
            return ResetPasswordResponse.builder()
                    .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                    .statusCode(ResetPasswordResponseStatusCode.USERNAME_VALIDATION_FAILED)
                    .build();
        }
        if(!validation.isAcceptablePassword(request)) {
            return ResetPasswordResponse.builder()
                    .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                    .statusCode(ResetPasswordResponseStatusCode.PASSWORD_VALIDATION_FAILED)
                    .build();
        }
        if(!request.getConfirmNewPassword().equals(request.getNewPassword())) {
            return ResetPasswordResponse.builder()
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .statusCode(ResetPasswordResponseStatusCode.NOT_MATCH_CONFIRM_PASSWORD)
                    .build();
        }
        return null;
    }
}
