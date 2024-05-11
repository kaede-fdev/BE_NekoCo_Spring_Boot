package VJames.Development.NingnenCo_BE.Infrastructure.Presentations.Controllers;

import VJames.Development.NingnenCo_BE.Application.Feature.Follow.AllFollowingUser.AllFollowingHandler;
import VJames.Development.NingnenCo_BE.Application.Feature.Follow.AllFollowingUser.AllFollowingRequest;
import VJames.Development.NingnenCo_BE.Application.Feature.Follow.SuggestPeople.SuggestPeopleHandler;
import VJames.Development.NingnenCo_BE.Application.Feature.Follow.SuggestPeople.SuggestPeopleRequest;
import VJames.Development.NingnenCo_BE.Infrastructure.Presentations.HttpResponse.HttpResponseMapper;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
@Tag(name = "Follow")
public class FollowController {
    //adding handlers dependence
    @Autowired
    private final SuggestPeopleHandler suggestPeopleHandler;
    @Autowired
    private final AllFollowingHandler allFollowingHandler;

    @Operation(summary = "Endpoint for getting suggest people to follow")
    @GetMapping("/suggest-people")
    private ResponseEntity<Map<String, Object>> getSuggestPeople(
            @RequestHeader("Authorization") String authToken
    ) {
        SuggestPeopleRequest featureRequest = SuggestPeopleRequest.builder()
                .authToken(authToken.substring(7)).build();
        Pipeline pipeline = new Pipelinr().with(() -> Stream.of(suggestPeopleHandler));
        var featureResponse = pipeline.send(featureRequest);
        return HttpResponseMapper.buildResponse(featureResponse.getHttpStatus(), featureResponse);
    }
    @Operation(summary = "Endpoint for getting all following user")
    @GetMapping("/all-following-people")
    private ResponseEntity<Map<String, Object>> getAllFollowingPeople(
            @RequestHeader("Authorization") String authToken
    ){
        AllFollowingRequest  featureRequest = AllFollowingRequest.builder()
                .authToken(authToken.substring(7))
                .build();
        Pipeline pipeline = new Pipelinr().with(() -> Stream.of(allFollowingHandler));
        var featureResponse = pipeline.send(featureRequest);
        return HttpResponseMapper.buildResponse(featureResponse.getHttpStatus(), featureResponse);
    }
}
