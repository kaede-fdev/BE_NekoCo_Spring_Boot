package VJames.Development.NingnenCo_BE.Infrastructure.Core.UserCase;

import VJames.Development.NingnenCo_BE.Application.UseCase.IVerifyUseCase;
import VJames.Development.NingnenCo_BE.Domain.Entities.Verify;
import VJames.Development.NingnenCo_BE.Domain.UnitOfWork.IUnitOfWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class VerificationUseCases implements IVerifyUseCase {
    @Autowired
    private IUnitOfWork _unitOfWork;

    @Override
    public Verify getVerifyFromVerifyToken(String token) {
        try {
            return _unitOfWork.getVerifyRepository().findByVerifyToken(token).orElseThrow();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public Boolean isVerifyTokenExpired(Verify verify) {
        return verify.getExpireAt().isBefore(LocalDateTime.now());
    }

    @Override
    public Boolean isAccountVerified(Verify verify) {
        return verify.getIsVerify();
    }

    @Override
    public Boolean isAccountVerified(String email) {
        Verify verify = _unitOfWork.getVerifyRepository().findByUserEmail(email).orElseThrow();
        return verify.getIsVerify();
    }

    @Override
    public void doVerifyAccount(Verify verify) {
        try {
            _unitOfWork.getVerifyRepository().updateVerifyStatusById(verify.getId(), true, LocalDateTime.now());
        } catch (Exception ex) {
            System.out.println(LocalDateTime.now() + "  Do verify account failed");
        }
    }

    @Override
    public String generateVerifyTokenUrl(String toEmail) {
        Verify verify = _unitOfWork.getVerifyRepository().findByUserEmail(toEmail).orElseThrow();
        String BASE_URL= "http://localhost:8080/api/v1/";
        System.out.println(LocalDateTime.now() + "  Get verify token: " + verify.getVerifyToken() + " of user: " + verify.getUserEmail());
        return BASE_URL+"auth/account/confirm?email=" + verify.getUserEmail() + "&verify-token=" + verify.getVerifyToken();
    }

    @Override
    public String generateVerifyTokenUrl(String toEmail, String newVerifyToken) {
        String BASE_URL= "http://localhost:8080/api/v1/";
        System.out.println(LocalDateTime.now() + "  Get verify token: " + newVerifyToken + " of user: " + toEmail);
        return BASE_URL+"auth/account/confirm?email=" + toEmail + "&verify-token=" + newVerifyToken;
    }

    @Override
    public void refreshVerifyToken(String userEmail, String newToken) {
        _unitOfWork.getVerifyRepository().updateVerifyForResendEmailByUserEmail(
                userEmail,
                newToken,
                LocalDateTime.now().plusMinutes(10), false, null
        );
        System.out.println(LocalDateTime.now() + "  Updated verify token for email" + userEmail);
    }
}
