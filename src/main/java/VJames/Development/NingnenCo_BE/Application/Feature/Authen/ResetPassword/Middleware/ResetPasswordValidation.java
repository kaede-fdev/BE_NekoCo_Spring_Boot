package VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResetPassword.Middleware;

import VJames.Development.NingnenCo_BE.Application.DefautStorage.IEmailFormat;
import VJames.Development.NingnenCo_BE.Application.DefautStorage.IPasswordRule;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResetPassword.ResetPasswordRequest;
import VJames.Development.NingnenCo_BE.Infrastructure.Core.DefautStorage.EmailFormat;
import VJames.Development.NingnenCo_BE.Infrastructure.Core.DefautStorage.PassRule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetPasswordValidation implements IResetPasswordValidation{
    private final IEmailFormat _emailFormat = new EmailFormat();
    private final IPasswordRule _passRule = new PassRule();
    @Override
    public Boolean isEmptyRequest(ResetPasswordRequest request) {
        return request.getPassCode().trim().isEmpty() ||
                request.getUserName().trim().isEmpty() ||
                request.getNewPassword().trim().isEmpty() ||
                request.getConfirmNewPassword().trim().isEmpty();
    }

    @Override
    public Boolean isValidEmailFormat(ResetPasswordRequest request) {
        Pattern pattern = Pattern.compile(_emailFormat.getPattern());
        Matcher matcher = pattern.matcher(request.getUserName());
        return matcher.matches();
    }

    @Override
    public Boolean isAcceptablePassword(ResetPasswordRequest request) {
        Pattern pattern = Pattern.compile(_passRule.getPattern());
        Matcher matcher = pattern.matcher(request.getNewPassword());
        return matcher.matches();
    }
}
