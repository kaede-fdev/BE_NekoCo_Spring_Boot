package VJames.Development.NingnenCo_BE.Application.Feature.Follow.SuggestPeople;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SuggestPeopleRequest implements Command<SuggestPeopleResponse> {
    private String authToken;
}
