package VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResetPassword;

public enum ResetPasswordResponseStatusCode {
    INPUT_EMPTY_VALUE,
    USERNAME_VALIDATION_FAILED,
    PASSWORD_VALIDATION_FAILED,
    USER_NOT_FOUND,
    PASSCODE_NOT_FOUND,
    PASSCODE_EXPIRED,
    RESET_PASSWORD_SUCCESS,
    NOT_MATCH_CONFIRM_PASSWORD,
}
