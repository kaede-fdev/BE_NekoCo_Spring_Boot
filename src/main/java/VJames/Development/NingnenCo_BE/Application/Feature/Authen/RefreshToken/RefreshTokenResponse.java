package VJames.Development.NingnenCo_BE.Application.Feature.Authen.RefreshToken;

import lombok.*;
import org.springframework.http.HttpStatus;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponse {
    private RefreshTokenResponseStatusCode statusCode;
    private HttpStatus  httpStatus;
    private String accessToken;
    private String refreshToken;
}

