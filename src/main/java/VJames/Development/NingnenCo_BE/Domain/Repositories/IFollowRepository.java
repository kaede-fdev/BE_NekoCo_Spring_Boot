package VJames.Development.NingnenCo_BE.Domain.Repositories;

import VJames.Development.NingnenCo_BE.Domain.Entities.Follow;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IFollowRepository {
    Follow findByUserIdAndFollowingId(String fromUser,String toUser);
    List<Object[]> findAllUserFollowingFromUserId(String userId);
    List<Object[]> findAllFollowsToUser(String userFollowed);
    Boolean checkIsFollowed(String userId, String followingId);
    List<Object[]> finalAllUserCanChatWith(String userId);
}
