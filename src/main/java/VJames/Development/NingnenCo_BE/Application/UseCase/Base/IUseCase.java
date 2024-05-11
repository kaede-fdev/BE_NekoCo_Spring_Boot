package VJames.Development.NingnenCo_BE.Application.UseCase.Base;

import VJames.Development.NingnenCo_BE.Application.UseCase.IAuthUseCase;
import VJames.Development.NingnenCo_BE.Application.UseCase.IEmailUseCase;
import VJames.Development.NingnenCo_BE.Application.UseCase.ITokenUseCase;
import VJames.Development.NingnenCo_BE.Application.UseCase.IVerifyUseCase;

public interface IUseCase {
    IAuthUseCase getAuthUseCase();
    IEmailUseCase getEmailUseCase();
    IVerifyUseCase getVerifyUseCase();
    ITokenUseCase getTokenUseCase();
}
