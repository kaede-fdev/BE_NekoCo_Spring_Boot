package VJames.Development.NingnenCo_BE.Application.Feature.Follow.SuggestPeople;

import VJames.Development.NingnenCo_BE.Domain.Dtos.UserDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
@Getter
public class SuggestPeopleResponse {
    private SuggestPeopleResponseStatusCode statusCode;
    private HttpStatus httpStatus;
    private List<UserDto> suggest;
}
