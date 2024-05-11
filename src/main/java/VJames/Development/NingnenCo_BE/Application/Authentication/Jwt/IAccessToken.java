package VJames.Development.NingnenCo_BE.Application.Authentication.Jwt;

import VJames.Development.NingnenCo_BE.Domain.Entities.User;

public interface IAccessToken {
    String generateAccessToken(User user, String refreshToken);
}
