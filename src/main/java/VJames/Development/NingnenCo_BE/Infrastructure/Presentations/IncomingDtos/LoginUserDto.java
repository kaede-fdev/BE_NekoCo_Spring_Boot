package VJames.Development.NingnenCo_BE.Infrastructure.Presentations.IncomingDtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto {
    private String username;
    private String password;
}
