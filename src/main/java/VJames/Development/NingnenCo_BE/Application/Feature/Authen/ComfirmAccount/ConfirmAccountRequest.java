package VJames.Development.NingnenCo_BE.Application.Feature.Authen.ComfirmAccount;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ConfirmAccountRequest implements Command<ConfirmAccountResponse> {
    String email;
    String verifyToken;
}
