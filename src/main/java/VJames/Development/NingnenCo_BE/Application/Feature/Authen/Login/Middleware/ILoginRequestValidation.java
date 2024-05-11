package VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.Middleware;

import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.LoginRequest;

public interface ILoginRequestValidation{
    boolean isRequestEmpty(LoginRequest request);
    boolean isValidPassword(LoginRequest request);
}
