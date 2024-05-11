package VJames.Development.NingnenCo_BE.Application.UseCase;

import java.util.Map;

public interface IEmailUseCase {
    Boolean isDeliverable(String email);
    void sendVerifycationEmail(String toEmail,Map<String, Object> model);
    void sendEmailToResetPassword(String toEmail, Map<String, Object> model);

}
