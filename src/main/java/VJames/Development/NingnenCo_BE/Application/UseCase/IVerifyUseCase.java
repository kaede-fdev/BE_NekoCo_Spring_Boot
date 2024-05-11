package VJames.Development.NingnenCo_BE.Application.UseCase;

import VJames.Development.NingnenCo_BE.Domain.Entities.Verify;

public interface IVerifyUseCase {
    Verify getVerifyFromVerifyToken(String token);
    Boolean isVerifyTokenExpired(Verify verify);
    Boolean isAccountVerified(Verify verify);
    Boolean isAccountVerified(String email);
    void doVerifyAccount(Verify verify);
    String generateVerifyTokenUrl(String toEmail);
    String generateVerifyTokenUrl(String toEmail, String newVerifyToken);
    void refreshVerifyToken(String email, String newToken);

}
