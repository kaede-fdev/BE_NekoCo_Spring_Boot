package VJames.Development.NingnenCo_BE.Application.Feature.Follow.AllFollowingUser;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserItemResponse {
    private String id;
    private String appUserName;
    private String firstName;
    private String lastName;
    private String avatar;
    private String followAt;
}
