package VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResendVerifyEmail;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResentVerifyEmailRequest implements Command<ResendVerifyEmailResponse> {
    private String email;
}
