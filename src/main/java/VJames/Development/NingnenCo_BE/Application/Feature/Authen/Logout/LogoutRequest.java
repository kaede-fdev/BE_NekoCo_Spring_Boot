package VJames.Development.NingnenCo_BE.Application.Feature.Authen.Logout;

import an.awesome.pipelinr.Command;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequest implements Command<LogoutResponse> {
    private String authorizationToken;
}
