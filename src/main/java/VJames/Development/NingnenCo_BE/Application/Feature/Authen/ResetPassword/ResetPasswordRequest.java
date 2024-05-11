package VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResetPassword;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ResetPasswordRequest implements Command<ResetPasswordResponse> {
    private String passCode;
    private String userName;
    private String newPassword;
    private String confirmNewPassword;
}
