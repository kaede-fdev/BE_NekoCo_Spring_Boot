package VJames.Development.NingnenCo_BE.Application.Feature.Follow.AllFollowingUser;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Setter
@Getter
@Builder
public class AllFollowingResponse {
    private AllFollowingResponseStatusCode statusCode;
    private HttpStatus httpStatus;
    private UserItemResponse body;
}
