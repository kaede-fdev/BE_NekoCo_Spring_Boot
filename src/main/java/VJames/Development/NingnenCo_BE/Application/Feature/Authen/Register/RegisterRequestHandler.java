package VJames.Development.NingnenCo_BE.Application.Feature.Authen.Register;

import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Register.Middleware.IRegisterMiddleware;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Register.Middleware.RegisterRequestValidatorMiddleware;
import VJames.Development.NingnenCo_BE.Application.UseCase.Base.IUseCase;
import VJames.Development.NingnenCo_BE.Domain.Dtos.UserDto;
import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class RegisterRequestHandler implements
        Command.Handler<RegisterRequest, RegisterResponse>,
        IRegisterMiddleware {
    @Autowired
    private final RegisterRequestValidatorMiddleware registerRequestValidatorMiddleware;
    @Autowired
    private final IUseCase _useCase;
    @Override
    public RegisterResponse handle(RegisterRequest registerRequest) {
        try {
            Pipeline pipeline = new Pipelinr()
                    .with(() -> Stream.of(registerRequestValidatorMiddleware));
            var middlewareRes = pipeline.send(registerRequest);
            if(middlewareRes != null) return middlewareRes;
            //checking existed user
            UserDto existedUser = _useCase.getAuthUseCase().findExistedUser(registerRequest.getEmail());
            if(existedUser != null ) {
                if(existedUser.getUsername().equals(registerRequest.getEmail())) {
                    return RegisterResponse.builder()
                            .httpStatus(HttpStatus.FOUND)
                            .statusCode(RegisterResponseStatusCode.USER_IS_EXISTED)
                            .build();
                }
            }
            UserDto newUser;
            if(_useCase.getEmailUseCase().isDeliverable(registerRequest.getEmail())) {
                newUser = _useCase.getAuthUseCase().register(registerRequest);
                String verifyAccountUrl = _useCase.getVerifyUseCase().generateVerifyTokenUrl(newUser.getEmail());
                System.out.println(LocalDateTime.now() + "  Sending verify url: " + verifyAccountUrl);
                _useCase.getEmailUseCase().sendVerifycationEmail(newUser.getEmail(), Map.of(
                        "username", newUser.getUsername(),
                        "verifyAddress", verifyAccountUrl
                ));
            } else return RegisterResponse.builder()
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .statusCode(RegisterResponseStatusCode.EMAIL_UNDELIVERABLE)
                    .build();
            return RegisterResponse.builder()
                    .statusCode(RegisterResponseStatusCode.REGISTER_OPERATION_SUCCESS)
                    .httpStatus(HttpStatus.OK)
                    .build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return RegisterResponse.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(RegisterResponseStatusCode.OPERATION_FAIL)
                    .build();
        }
    }
}
