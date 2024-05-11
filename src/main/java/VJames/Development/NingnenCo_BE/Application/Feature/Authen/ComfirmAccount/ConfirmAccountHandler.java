package VJames.Development.NingnenCo_BE.Application.Feature.Authen.ComfirmAccount;

import VJames.Development.NingnenCo_BE.Application.Feature.Authen.ComfirmAccount.Middleware.IConfirmAccountMiddleware;
import VJames.Development.NingnenCo_BE.Application.UseCase.Base.IUseCase;
import VJames.Development.NingnenCo_BE.Domain.Entities.Verify;
import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfirmAccountHandler implements
        Command.Handler<ConfirmAccountRequest, ConfirmAccountResponse>,
        IConfirmAccountMiddleware {
    private final IUseCase _useCase;
    @Override
    public ConfirmAccountResponse handle(ConfirmAccountRequest request) {
        try {
            Verify foundVerify = _useCase.getVerifyUseCase().getVerifyFromVerifyToken(request.getVerifyToken());
            if(foundVerify != null) {
                if(_useCase.getVerifyUseCase().isAccountVerified(foundVerify)) {
                    return ConfirmAccountResponse.builder()
                            .httpStatus(HttpStatus.ALREADY_REPORTED)
                            .statusCode(ConfirmAccountResponseStatusCode.ALREADY_VERIFY)
                            .build();
                }
                if(!_useCase.getVerifyUseCase().isVerifyTokenExpired(foundVerify)) {
                    _useCase.getVerifyUseCase().doVerifyAccount(foundVerify);
                } else return ConfirmAccountResponse.builder()
                        .httpStatus(HttpStatus.EXPECTATION_FAILED)
                        .statusCode(ConfirmAccountResponseStatusCode.VERIFY_TOKEN_EXPIRED)
                        .build();
            } else {
                return  ConfirmAccountResponse.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .statusCode(ConfirmAccountResponseStatusCode.INVALID_VERIFY_TOKEN)
                        .build();
            }
            return ConfirmAccountResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .statusCode(ConfirmAccountResponseStatusCode.VERIFY_ACCOUNT_SUCCESS)
                    .build();
        } catch (Exception ex) {
            return null;
        }
    }
}
