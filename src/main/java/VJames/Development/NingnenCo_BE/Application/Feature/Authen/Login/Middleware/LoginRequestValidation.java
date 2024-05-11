package VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.Middleware;

import VJames.Development.NingnenCo_BE.Application.DefautStorage.IPasswordRule;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.LoginRequest;
import VJames.Development.NingnenCo_BE.Infrastructure.Core.DefautStorage.PassRule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginRequestValidation implements ILoginRequestValidation{
    private final IPasswordRule _passRule = new PassRule();
    @Override
    public boolean isRequestEmpty(LoginRequest request) {
        if(request.getUsername().isEmpty()) {
            return true;
        }
        return request.getPassword().isEmpty();
    }

    @Override
    public boolean isValidPassword(LoginRequest request) {
        Pattern pattern = Pattern.compile(_passRule.getPattern());
        Matcher matcher = pattern.matcher(request.getPassword());
        return matcher.matches();
    }
}
