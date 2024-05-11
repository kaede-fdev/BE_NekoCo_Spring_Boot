package VJames.Development.NingnenCo_BE.Application.Feature.Authen.Register;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Builder
@Getter
public class RegisterResponse {
    private RegisterResponseStatusCode statusCode;
    private HttpStatus httpStatus;
}
