package VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.Middleware;

import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.LoginRequest;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.LoginResponse;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.LoginResponseStatusCode;
import an.awesome.pipelinr.Command;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class LoginRequestValidatorMiddleware implements
        Command.Handler<LoginRequest, LoginResponse>,
        ILoginMiddleware {
    @Override
    public LoginResponse handle(LoginRequest loginRequest) {
        ILoginRequestValidation loginRequestValidation = new LoginRequestValidation();
        if (loginRequestValidation.isRequestEmpty(loginRequest)){
            return LoginResponse.builder()
                    .statusCode(LoginResponseStatusCode.INPUT_EMPTY_VALUE)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
        if (!loginRequestValidation.isValidPassword(loginRequest)) {
            return LoginResponse.builder()
                    .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                    .statusCode(LoginResponseStatusCode.INVALID_PASSWORD_FORMAT)
                    .build();
        }
        return null;
    }
}
