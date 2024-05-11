package VJames.Development.NingnenCo_BE.Application.Feature.Authen.Register;

public enum RegisterResponseStatusCode {
    INPUT_EMPTY_VALUE,
    EMAIL_VALIDATION_FAIL,
    PASSWORD_VALIDATION_FAIL,
    INVALID_PASSWORD_FORMAT,
    REGISTER_OPERATION_SUCCESS,
    OPERATION_FAIL,
    USER_IS_EXISTED,
    EMAIL_UNDELIVERABLE
}
