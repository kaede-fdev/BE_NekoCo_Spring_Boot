package VJames.Development.NingnenCo_BE.Infrastructure.Presentations.IncomingDtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
    private String email;
    private String password;
    private String userRole;
}
