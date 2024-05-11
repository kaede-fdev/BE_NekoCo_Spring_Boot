package VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.Response;

import lombok.Builder;

@Builder
public class Body {
    public String accessToken;
    public String refreshToken;

}
