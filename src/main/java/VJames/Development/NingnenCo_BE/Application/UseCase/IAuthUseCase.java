package VJames.Development.NingnenCo_BE.Application.UseCase;

import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.LoginRequest;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.LoginResponse;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Register.RegisterRequest;
import VJames.Development.NingnenCo_BE.Domain.Dtos.UserDto;

public interface IAuthUseCase {
    LoginResponse authentication (LoginRequest request);
    UserDto register (RegisterRequest request);
    Boolean isAccountVerify(String userEmail);
    UserDto findExistedUser(String username);
}
