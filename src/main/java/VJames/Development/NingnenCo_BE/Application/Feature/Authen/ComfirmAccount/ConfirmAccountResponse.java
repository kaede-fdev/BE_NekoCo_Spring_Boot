package VJames.Development.NingnenCo_BE.Application.Feature.Authen.ComfirmAccount;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class ConfirmAccountResponse {
    private ConfirmAccountResponseStatusCode statusCode;
    private HttpStatus httpStatus;
}
