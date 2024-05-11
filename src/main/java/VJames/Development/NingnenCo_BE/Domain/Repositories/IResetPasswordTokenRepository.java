package VJames.Development.NingnenCo_BE.Domain.Repositories;

import VJames.Development.NingnenCo_BE.Domain.Entities.ResetPasswordToken;

public interface IResetPasswordTokenRepository {
    ResetPasswordToken findByUserId(String userId);
    ResetPasswordToken findByResetCode(String resetCode);
    void deleteById(String id);
    ResetPasswordToken save(ResetPasswordToken token);
}
