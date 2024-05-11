package VJames.Development.NingnenCo_BE.Infrastructure.Core.UserCase;

import VJames.Development.NingnenCo_BE.Application.UseCase.IEmailUseCase;
import VJames.Development.NingnenCo_BE.Domain.UnitOfWork.IUnitOfWork;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailUseCases implements IEmailUseCase {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final IUnitOfWork _unitOfWork;
    private final Configuration configuration;
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sendFrom;

    private String requestToCheckingValidEmailAddress(String email) {
        try {
            String apiUrl = "https://emailvalidation.abstractapi.com/v1/" +
                    "?api_key=df6b2a67009d4255bc084306de531aeb&email=" + email;
            WebClient webClient = WebClient.create();
            String responseBody = webClient.get()
                    .uri(apiUrl)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("deliverability").asText();
        } catch (Exception error) {
            System.out.println(error.getMessage());
            return null;
        }
    }
    @Override
    public Boolean isDeliverable(String email) {
//        String deliverableStatus = requestToCheckingValidEmailAddress(email);
//        System.out.println(LocalDateTime.now() + "  " + email + " Deliverable status: " + deliverableStatus);
//        if(deliverableStatus.equals("UNKNOWN")) return false;
//        return !deliverableStatus.equals("UNDELIVERABLE");
        return true;
    }

    @Override
    public void sendVerifycationEmail(String toEmail, Map<String, Object> model) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
            Template t = configuration.getTemplate("verification-email.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.setSubject("Welcome to MeowCo, let's verify your account to access the application!");
            mimeMessageHelper.setFrom(sendFrom);
            mailSender.send(message);
            System.out.println(LocalDateTime.now() + "  Sent email to " + toEmail + " successfully!");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void sendEmailToResetPassword(String toEmail, Map<String, Object> model) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
            Template t = configuration.getTemplate("reset-password-email.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.setSubject("From MeowCo | Reset your password");
            mimeMessageHelper.setFrom(sendFrom);
            mailSender.send(message);
            System.out.println(LocalDateTime.now() + "  Sent reset password email to " + toEmail + " successfully!");
            //todo
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
