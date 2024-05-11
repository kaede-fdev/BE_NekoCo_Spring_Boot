package VJames.Development.NingnenCo_BE.Application.Feature.Authen.RefreshToken.MainDataResponse;

import lombok.Builder;

@Builder
public class Body {
    private String accessToken;
    private String refreshToken;
}
