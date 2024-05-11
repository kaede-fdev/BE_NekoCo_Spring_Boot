package VJames.Development.NingnenCo_BE.Infrastructure.Core.Repositories;

import VJames.Development.NingnenCo_BE.Domain.Entities.ResetPasswordToken;
import VJames.Development.NingnenCo_BE.Domain.Repositories.IResetPasswordTokenRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordTokenRepository extends
        JpaRepository<ResetPasswordToken, String>,
        IResetPasswordTokenRepository {
    @Override
    ResetPasswordToken findByUserId(String userId);

    @Override
    ResetPasswordToken findByResetCode(String resetCode);
}
