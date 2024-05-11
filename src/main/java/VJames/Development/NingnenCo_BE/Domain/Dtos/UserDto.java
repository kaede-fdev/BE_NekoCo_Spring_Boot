package VJames.Development.NingnenCo_BE.Domain.Dtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String id;
    private String email;
    private String username;
    private String appUserName;
    private String status;
    private String firstName;
    private String lastName;
    private String avatar;
    private String role;
    private String disconnectAt;
}
