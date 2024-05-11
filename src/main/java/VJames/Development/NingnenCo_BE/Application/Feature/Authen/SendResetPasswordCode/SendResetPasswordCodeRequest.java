package VJames.Development.NingnenCo_BE.Application.Feature.Authen.SendResetPasswordCode;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SendResetPasswordCodeRequest implements Command<SendResetPasswordCodeResponse> {
    private String email;
}
