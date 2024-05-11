package VJames.Development.NingnenCo_BE.Application.Feature.Authen.Register.Middleware;

import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Register.RegisterRequest;

public interface IRegisterRequestValidation {
    Boolean isRequestEmpty(RegisterRequest request);
    Boolean isValidEmailFormat(RegisterRequest request);
    Boolean isAcceptablePassword(RegisterRequest request);
}
