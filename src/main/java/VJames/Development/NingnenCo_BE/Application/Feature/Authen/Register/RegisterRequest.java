package VJames.Development.NingnenCo_BE.Application.Feature.Authen.Register;

import an.awesome.pipelinr.Command;
import lombok.*;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class RegisterRequest implements Command<RegisterResponse> {
    private String email;
    private String password;
    private String userRole;
}
