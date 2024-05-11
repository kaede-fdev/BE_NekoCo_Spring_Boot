package VJames.Development.NingnenCo_BE.Application.Feature.Authen.Register.Middleware;

import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Register.RegisterRequest;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Register.RegisterResponse;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Register.RegisterResponseStatusCode;
import an.awesome.pipelinr.Command;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
@Component
public class RegisterRequestValidatorMiddleware implements
        Command.Handler<RegisterRequest, RegisterResponse>,
        IRegisterMiddleware {
    @Override
    public RegisterResponse handle(RegisterRequest registerRequest) {
        IRegisterRequestValidation registerRequestValidation = new RegisterRequestValidation();
        if(registerRequestValidation.isRequestEmpty(registerRequest)) {
            return RegisterResponse.builder()
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .statusCode(RegisterResponseStatusCode.INPUT_EMPTY_VALUE)
                    .build();
        } else if(!registerRequestValidation.isValidEmailFormat(registerRequest)) {
            return RegisterResponse.builder()
                    .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                    .statusCode(RegisterResponseStatusCode.EMAIL_VALIDATION_FAIL)
                    .build();
        } else if(!registerRequestValidation.isAcceptablePassword(registerRequest)) {
            System.out.println(registerRequestValidation.isAcceptablePassword(registerRequest));
            return RegisterResponse.builder()
                    .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                    .statusCode(RegisterResponseStatusCode.PASSWORD_VALIDATION_FAIL)
                    .build();
        }
        return null;
    }
}
