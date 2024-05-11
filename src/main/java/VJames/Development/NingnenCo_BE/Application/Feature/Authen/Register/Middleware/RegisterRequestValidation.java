package VJames.Development.NingnenCo_BE.Application.Feature.Authen.Register.Middleware;

import VJames.Development.NingnenCo_BE.Application.DefautStorage.IEmailFormat;
import VJames.Development.NingnenCo_BE.Application.DefautStorage.IPasswordRule;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Register.RegisterRequest;
import VJames.Development.NingnenCo_BE.Infrastructure.Core.DefautStorage.EmailFormat;
import VJames.Development.NingnenCo_BE.Infrastructure.Core.DefautStorage.PassRule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterRequestValidation implements IRegisterRequestValidation{
    private final IEmailFormat _emailFormat = new EmailFormat();
    private final IPasswordRule _passRule = new PassRule();
    @Override
    public Boolean isRequestEmpty(RegisterRequest request) {
        if(request.getEmail().trim().isEmpty()) {
            return true;
        }
        return request.getPassword().trim().isEmpty();
    }

    @Override
    public Boolean isValidEmailFormat(RegisterRequest request) {
        Pattern pattern = Pattern.compile(_emailFormat.getPattern());
        Matcher matcher = pattern.matcher(request.getEmail());
        return matcher.matches();
    }

    @Override
    public Boolean isAcceptablePassword(RegisterRequest request) {
        Pattern pattern = Pattern.compile(_passRule.getPattern());
        Matcher matcher = pattern.matcher(request.getPassword());
        return matcher.matches();
    }
}
