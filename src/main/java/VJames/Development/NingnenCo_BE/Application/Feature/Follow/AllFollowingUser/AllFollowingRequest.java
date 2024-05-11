package VJames.Development.NingnenCo_BE.Application.Feature.Follow.AllFollowingUser;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class AllFollowingRequest implements Command<AllFollowingResponse> {
    private String authToken;
}
