package VJames.Development.NingnenCo_BE.Application.Feature.Follow.AllFollowingUser;

import VJames.Development.NingnenCo_BE.Application.Feature.Follow.AllFollowingUser.Middleware.IAllFollowingMiddleware;
import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AllFollowingHandler implements
        Command.Handler<AllFollowingRequest, AllFollowingResponse>,
        IAllFollowingMiddleware {
    @Override
    public AllFollowingResponse handle(AllFollowingRequest request) {
        if(!request.getAuthToken().isEmpty()) {
        }
        return AllFollowingResponse.builder()
                .statusCode(AllFollowingResponseStatusCode.OPERATION_FAILED)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }
}
