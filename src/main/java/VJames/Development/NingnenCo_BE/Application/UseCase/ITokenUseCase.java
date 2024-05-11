package VJames.Development.NingnenCo_BE.Application.UseCase;

import VJames.Development.NingnenCo_BE.Domain.Entities.ResetPasswordToken;

public interface ITokenUseCase {
    void createAndSendResetPassCodeToUserWithId(String userId, String email);
    void getPassCodeAndUpdatingUserPassword(String newPassWord, ResetPasswordToken passwordToken);
}
