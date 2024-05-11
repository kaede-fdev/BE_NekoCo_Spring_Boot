package VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login;

import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.Middleware.ILoginMiddleware;
import an.awesome.pipelinr.Command;
import lombok.*;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Builder
public class LoginRequest implements Command<LoginResponse>, ILoginMiddleware {
    private String username;
    private String password;
}
