package VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResetPassword.Middleware;

import VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResetPassword.ResetPasswordRequest;

public interface IResetPasswordValidation {
    Boolean isEmptyRequest (ResetPasswordRequest request);
    Boolean isValidEmailFormat(ResetPasswordRequest request);
    Boolean isAcceptablePassword(ResetPasswordRequest request);
}
