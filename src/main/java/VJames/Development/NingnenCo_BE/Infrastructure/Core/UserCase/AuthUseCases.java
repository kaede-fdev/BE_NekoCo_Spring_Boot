package VJames.Development.NingnenCo_BE.Infrastructure.Core.UserCase;

import VJames.Development.NingnenCo_BE.Application.Authentication.Jwt.IAccessToken;
import VJames.Development.NingnenCo_BE.Application.Authentication.Jwt.IRefreshToken;
import VJames.Development.NingnenCo_BE.Application.DefautStorage.IDefaultImage;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.*;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.Response.Body;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.Response.UserCredential;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Register.RegisterRequest;
import VJames.Development.NingnenCo_BE.Application.UseCase.IAuthUseCase;
import VJames.Development.NingnenCo_BE.Domain.Dtos.UserDto;
import VJames.Development.NingnenCo_BE.Domain.DtosMapper.UserDtoMapper;
import VJames.Development.NingnenCo_BE.Domain.Entities.Token;
import VJames.Development.NingnenCo_BE.Domain.Entities.User;
import VJames.Development.NingnenCo_BE.Domain.Entities.Verify;
import VJames.Development.NingnenCo_BE.Domain.Enums.Role;
import VJames.Development.NingnenCo_BE.Domain.UnitOfWork.IUnitOfWork;
import VJames.Development.NingnenCo_BE.Infrastructure.Core.DefautStorage.DefaultImage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthUseCases implements IAuthUseCase {
    private final AuthenticationManager _authenticationManager;
    private final IUnitOfWork _unitOfWork;
    private final IAccessToken _accessToken;
    private final IRefreshToken _refreshToken;
    private final PasswordEncoder _passwordEncoder;

    @Override
    public LoginResponse authentication(LoginRequest request) {
        try {
            _authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            //get user from db
            User user = _unitOfWork.getUserRepository()
                    .findByUsername(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found in database"));
            //checking is account verify
            if(!isAccountVerify(user.getUsername())) {
                return LoginResponse.builder()
                        .statusCode(LoginResponseStatusCode.USER_EMAIL_IS_NOT_CONFIRMED)
                        .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                        .build();
            }
            if(user.isCredentialsNonExpired()) {
                String newRefreshToken = _refreshToken.generateRefreshToken();
                String newAccessToken = _accessToken.generateAccessToken(user, newRefreshToken);
                List<Token> tokens = _unitOfWork.getTokenRepository().findAllByUserId(user.getId());
                int TOKEN_MAX_COUNT = 4;
                int currentTokenCount = tokens.size();
                if(currentTokenCount >= TOKEN_MAX_COUNT) {
                    Token tokenToDelete =
                            _unitOfWork.getTokenRepository().findEarliestExpiredTokenByUserId(user.getId());
                    _unitOfWork.getTokenRepository().deleteByRefreshToken(tokenToDelete.getRefreshToken());
                    System.out.println(LocalDateTime.now() + "  Deleted tokenId = " + tokenToDelete.getId());
                }
                int REFRESHTOKEN_EXPIRATION_TIME = 14;
                _unitOfWork.getTokenRepository().save(Token.builder()
                                .userId(user.getId())
                                .tokenType("Bearer")
                                .refreshToken(newRefreshToken)
                                .refreshTokenExpirationTime(LocalDateTime.now().plusDays(REFRESHTOKEN_EXPIRATION_TIME))
                                .build());
                return LoginResponse.builder()
                        .statusCode(LoginResponseStatusCode.LOGIN_SUCCESS)
                        .httpStatus(HttpStatus.OK)
                        .body(Body.builder()
                                .accessToken(newAccessToken)
                                .refreshToken(newRefreshToken)
                                .build())
                        .userCredential(UserCredential.builder()
                                .email(user.getEmail())
                                .avatar(user.getAvatar())
                                .build())
                        .build();
            } else throw new RuntimeException("User password has expired");

        } catch (AuthenticationException ex) {
            return LoginResponse.builder()
                    .statusCode(LoginResponseStatusCode.USER_PASSWORD_IS_NOT_CORRECT)
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .build();
        }
    }
    private String extractEmailName(String email) {
        if(!email.contains("@") || !email.contains(".")) {
            return null;
        }
        String[] emailsParts = email.split("@");
        return emailsParts[0];
    }
    @Override
    public UserDto register(RegisterRequest request) {
        IDefaultImage defaultImage = new DefaultImage();
        User user = User.builder()
                .email(request.getEmail())
                .username(request.getEmail())
                .appUserName(extractEmailName(request.getEmail()))
                .avatar(defaultImage.getImage())
                .password(_passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getUserRole()))
                .build();
        User savedUser = _unitOfWork.getUserRepository().save(user);
        Verify verify = Verify.builder()
                .userId(user.getId())
                .userEmail(user.getEmail())
                .verifyToken(UUID.randomUUID().toString())
                .isVerify(false)
                .expireAt(LocalDateTime.now().plusMinutes(10))
                .build();
        _unitOfWork.getVerifyRepository().save(verify);
        return UserDtoMapper.toUserDto(savedUser);
    }

    @Override
    public Boolean isAccountVerify(String userEmail) {
        Verify verify = _unitOfWork.getVerifyRepository().findByUserEmail(userEmail).orElseThrow();
        return verify.getIsVerify();
    }

    @Override
    public UserDto findExistedUser(String username ) {
        try {
            return UserDtoMapper.toUserDto(_unitOfWork.getUserRepository().findByUsername(username).orElseThrow());
        } catch (Exception ex) {
            System.out.println(LocalDateTime.now() + "  User with email: " + username + " doesn't in " +
                    "database yet! Creating new user ->>");
        }
        return null;
    }
}
