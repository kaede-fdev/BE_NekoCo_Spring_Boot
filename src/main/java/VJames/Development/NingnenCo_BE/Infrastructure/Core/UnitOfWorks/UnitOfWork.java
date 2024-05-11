package VJames.Development.NingnenCo_BE.Infrastructure.Core.UnitOfWorks;

import VJames.Development.NingnenCo_BE.Domain.Repositories.*;
import VJames.Development.NingnenCo_BE.Domain.UnitOfWork.IUnitOfWork;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Getter
@RequiredArgsConstructor
public class UnitOfWork implements IUnitOfWork {
    @Autowired
    private final IUserRepository userRepository;
    @Autowired
    private final ITokenRepository tokenRepository;
    @Autowired
    private final IVerifyRepository verifyRepository;
    @Autowired
    private final IResetPasswordTokenRepository resetPasswordTokenRepository;
    @Autowired
    private final IFollowRepository followRepository;

}
