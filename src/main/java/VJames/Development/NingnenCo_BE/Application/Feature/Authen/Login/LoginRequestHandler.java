package VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login;

import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.Middleware.ILoginMiddleware;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.Middleware.LoginRequestValidatorMiddleware;
import VJames.Development.NingnenCo_BE.Application.UseCase.Base.IUseCase;
import VJames.Development.NingnenCo_BE.Domain.Dtos.UserDto;
import VJames.Development.NingnenCo_BE.Domain.DtosMapper.UserDtoMapper;
import VJames.Development.NingnenCo_BE.Domain.UnitOfWork.IUnitOfWork;
import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class LoginRequestHandler implements
        Command.Handler<LoginRequest, LoginResponse>,
        ILoginMiddleware {
    @Autowired
    private final IUnitOfWork _unitOfWork;
    private final IUseCase _useCase;

    @Override
    public LoginResponse handle(LoginRequest loginRequest) {
        Pipeline pipeline = new Pipelinr().with(() -> Stream.of(new LoginRequestValidatorMiddleware()));
        var middlewareRes = loginRequest.execute(pipeline);
        if (middlewareRes != null)
            return middlewareRes;
        UserDto userDto = UserDtoMapper.toUserDto(
                _unitOfWork.getUserRepository().findByUsername(loginRequest.getUsername()).orElseThrow());
        if (userDto == null) {
            return LoginResponse.builder()
                    .statusCode(LoginResponseStatusCode.DATABASE_OPERATION_FAIL)
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
        return _useCase.getAuthUseCase().authentication(loginRequest);
    }
}
