package VJames.Development.NingnenCo_BE.Infrastructure.Core.UserCase;

import VJames.Development.NingnenCo_BE.Application.UseCase.IEmailUseCase;
import VJames.Development.NingnenCo_BE.Application.UseCase.ITokenUseCase;
import VJames.Development.NingnenCo_BE.Application.Utils.RandomSequenceGenerator;
import VJames.Development.NingnenCo_BE.Domain.Dtos.UserDto;
import VJames.Development.NingnenCo_BE.Domain.DtosMapper.UserDtoMapper;
import VJames.Development.NingnenCo_BE.Domain.Entities.ResetPasswordToken;
import VJames.Development.NingnenCo_BE.Domain.UnitOfWork.IUnitOfWork;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TokenUseCases implements ITokenUseCase {
    @Autowired
    private final IUnitOfWork _unitOfWork;
    @Autowired
    private final IEmailUseCase _emailUseCase;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createAndSendResetPassCodeToUserWithId(String userId, String email) {
        String passCode = RandomSequenceGenerator.generateRandomSequenceWithText();
        ResetPasswordToken resetPasswordToken = _unitOfWork.getResetPasswordTokenRepository().findByUserId(userId);
        if(resetPasswordToken != null) {
            //presented
            _unitOfWork.getResetPasswordTokenRepository().deleteById(resetPasswordToken.getId());
        }
        _unitOfWork.getResetPasswordTokenRepository().save(ResetPasswordToken.builder()
                .resetCode(passCode).userId(userId).expireAt(LocalDateTime.now().plusMinutes(10))
                .build());
        _emailUseCase.sendEmailToResetPassword(email, Map.of(
                "passCode", passCode
        ));
    }

    @Override
    public void getPassCodeAndUpdatingUserPassword(String newPassword ,ResetPasswordToken passwordToken) {
        System.out.println(passwordToken.getUserId());
       try {
           UserDto foundUser =
                   UserDtoMapper.toUserDto(_unitOfWork.getUserRepository().findById(passwordToken.getUserId()).orElseThrow());
           if(foundUser != null && Objects.equals(passwordToken.getUserId(), foundUser.getId())) {
               //todo
               _unitOfWork.getUserRepository().updatePassword(passwordEncoder.encode(newPassword), passwordToken.getUserId());
               _unitOfWork.getResetPasswordTokenRepository().deleteById(passwordToken.getId());
           }
       } catch (Exception ex) {
           System.out.println(ex.getMessage());
       }
    }
}
