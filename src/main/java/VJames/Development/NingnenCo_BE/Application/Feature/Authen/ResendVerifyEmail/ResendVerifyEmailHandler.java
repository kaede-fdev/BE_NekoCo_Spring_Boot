package VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResendVerifyEmail;

import VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResendVerifyEmail.Middleware.IResendVerifyEmailMiddleware;
import VJames.Development.NingnenCo_BE.Application.UseCase.Base.IUseCase;
import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ResendVerifyEmailHandler implements
        Command.Handler<ResentVerifyEmailRequest, ResendVerifyEmailResponse>,
        IResendVerifyEmailMiddleware {
    @Autowired
    private final IUseCase _useCase;
    @Override
    public ResendVerifyEmailResponse handle(ResentVerifyEmailRequest request) {
      try {
          if(_useCase.getVerifyUseCase().isAccountVerified(request.getEmail())) {
              return ResendVerifyEmailResponse.builder()
                      .httpStatus(HttpStatus.ALREADY_REPORTED)
                      .statusCode(ResendVerifyEmailResponseStatusCode.ALREADY_VERIFY)
                      .build();
          }
          String newVerifyToken = UUID.randomUUID().toString();
          _useCase.getVerifyUseCase().refreshVerifyToken(request.getEmail(), newVerifyToken);
          String newVerifyAddress = _useCase.getVerifyUseCase().generateVerifyTokenUrl(request.getEmail(), newVerifyToken);
          System.out.println(LocalDateTime.now() + "  Resending verification address: " + newVerifyAddress);
          _useCase.getEmailUseCase().sendVerifycationEmail(request.getEmail(), Map.of(
                  "username", request.getEmail(),
                  "verifyAddress", newVerifyAddress
          ));
          return ResendVerifyEmailResponse.builder()
                  .httpStatus(HttpStatus.OK)
                  .statusCode(ResendVerifyEmailResponseStatusCode.RESEND_VERIFY_EMAIL_SUCCESS)
                  .build();
      } catch (Exception ex) {
          return ResendVerifyEmailResponse.builder()
                  .statusCode(ResendVerifyEmailResponseStatusCode.OPERATION_FAILED)
                  .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                  .build();
      }
    }
}
