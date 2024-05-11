package VJames.Development.NingnenCo_BE.Application.Feature.Follow.SuggestPeople;

import VJames.Development.NingnenCo_BE.Application.Feature.Follow.SuggestPeople.Middleware.ISuggestPeopleMiddleware;
import VJames.Development.NingnenCo_BE.Domain.Dtos.UserDto;
import VJames.Development.NingnenCo_BE.Domain.DtosMapper.UserDtoMapper;
import VJames.Development.NingnenCo_BE.Domain.Entities.User;
import VJames.Development.NingnenCo_BE.Domain.UnitOfWork.IUnitOfWork;
import VJames.Development.NingnenCo_BE.Infrastructure.Core.IdentifyService.JWT.IJwtService;
import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SuggestPeopleHandler implements
        Command.Handler<SuggestPeopleRequest, SuggestPeopleResponse>,
        ISuggestPeopleMiddleware {
    @Autowired
    private final IJwtService _jwt;
    @Autowired
    private final IUnitOfWork _unitOfWork;
    @Override
    public SuggestPeopleResponse handle(SuggestPeopleRequest request) {
        String userIdFromToken = _jwt.extractUserId(request.getAuthToken());
        if(!userIdFromToken.isEmpty()) {
            List<User> users = _unitOfWork.getUserRepository().findAllIfNotFollowingByCurrenUserId(userIdFromToken);
            List<UserDto> userDtos = UserDtoMapper.toUserDtoList(users);
            return SuggestPeopleResponse.builder()
                    .statusCode(SuggestPeopleResponseStatusCode.OPERATION_SUCCESS)
                    .httpStatus(HttpStatus.OK)
                    .suggest(userDtos)
                    .build();
        }
        return SuggestPeopleResponse.builder()
                .statusCode(SuggestPeopleResponseStatusCode.OPERATION_FAILED)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }
}
