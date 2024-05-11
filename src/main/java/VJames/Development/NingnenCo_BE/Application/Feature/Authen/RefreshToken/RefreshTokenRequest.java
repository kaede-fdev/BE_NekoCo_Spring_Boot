package VJames.Development.NingnenCo_BE.Application.Feature.Authen.RefreshToken;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RefreshTokenRequest implements Command<RefreshTokenResponse> {
    private String accessToken;
    private String refreshToken;
}
