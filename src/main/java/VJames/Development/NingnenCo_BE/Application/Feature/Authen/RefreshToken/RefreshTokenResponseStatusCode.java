package VJames.Development.NingnenCo_BE.Application.Feature.Authen.RefreshToken;

public enum RefreshTokenResponseStatusCode {
    REFRESH_SUCCESS,
    NOT_FOUND_REFRESH_TOKEN,
    REFRESH_TOKEN_EXPIRED,
    REFRESH_ACCESS_TOKEN_ERROR,
    REFRESH_TOKEN_NOT_MATCH_INNER_AUTH
}
