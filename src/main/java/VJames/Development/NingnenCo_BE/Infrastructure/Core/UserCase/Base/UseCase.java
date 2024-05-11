package VJames.Development.NingnenCo_BE.Infrastructure.Core.UserCase.Base;

import VJames.Development.NingnenCo_BE.Application.UseCase.Base.IUseCase;
import VJames.Development.NingnenCo_BE.Application.UseCase.IAuthUseCase;
import VJames.Development.NingnenCo_BE.Application.UseCase.IEmailUseCase;
import VJames.Development.NingnenCo_BE.Application.UseCase.ITokenUseCase;
import VJames.Development.NingnenCo_BE.Application.UseCase.IVerifyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UseCase implements IUseCase {
    @Autowired
    private final IAuthUseCase iAuthUseCase;
    @Autowired
    private final IEmailUseCase iEmailUseCase;
    @Autowired
    private final IVerifyUseCase iVerifyUseCase;
    @Autowired
    private final ITokenUseCase iTokenUseCase;
    @Override
    public IAuthUseCase getAuthUseCase() {
        return iAuthUseCase;
    }

    @Override
    public IEmailUseCase getEmailUseCase() {
        return iEmailUseCase;
    }

    @Override
    public IVerifyUseCase getVerifyUseCase() {
        return iVerifyUseCase;
    }

    @Override
    public ITokenUseCase getTokenUseCase() {
        return iTokenUseCase;
    }
}
