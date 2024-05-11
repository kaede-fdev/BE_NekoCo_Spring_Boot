package VJames.Development.NingnenCo_BE.Infrastructure.Presentations.IncomingDtos;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDto {
    private String passCode;
    private String userName;
    private String newPassword;
    private String confirmNewPassword;
}
