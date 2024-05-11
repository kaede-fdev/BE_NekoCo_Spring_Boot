package VJames.Development.NingnenCo_BE.Domain.UnitOfWork;

import VJames.Development.NingnenCo_BE.Domain.Repositories.*;

public interface IUnitOfWork {
    IUserRepository getUserRepository();
    ITokenRepository getTokenRepository();
    IVerifyRepository getVerifyRepository();
    IResetPasswordTokenRepository getResetPasswordTokenRepository();
    IFollowRepository getFollowRepository();

}
