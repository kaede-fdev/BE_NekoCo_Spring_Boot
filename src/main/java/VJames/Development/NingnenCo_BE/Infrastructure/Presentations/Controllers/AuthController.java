package VJames.Development.NingnenCo_BE.Infrastructure.Presentations.Controllers;

import java.util.Map;
import java.util.stream.Stream;

import VJames.Development.NingnenCo_BE.Application.Feature.Authen.ComfirmAccount.ConfirmAccountHandler;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.ComfirmAccount.ConfirmAccountRequest;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Logout.LogoutHandler;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Logout.LogoutRequest;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.RefreshToken.RefreshTokenHandler;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.RefreshToken.RefreshTokenRequest;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Register.RegisterRequest;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Register.RegisterRequestHandler;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResendVerifyEmail.ResendVerifyEmailHandler;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResendVerifyEmail.ResentVerifyEmailRequest;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResetPassword.ResetPasswordHandler;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResetPassword.ResetPasswordRequest;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.SendResetPasswordCode.SendResetPasswordCodeHandler;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.SendResetPasswordCode.SendResetPasswordCodeRequest;
import VJames.Development.NingnenCo_BE.Infrastructure.Presentations.IncomingDtos.LoginUserDto;
import VJames.Development.NingnenCo_BE.Infrastructure.Presentations.IncomingDtos.RefreshAccessTokenDto;
import VJames.Development.NingnenCo_BE.Infrastructure.Presentations.IncomingDtos.RegisterUserDto;
import VJames.Development.NingnenCo_BE.Infrastructure.Presentations.IncomingDtos.ResetPasswordDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.LoginRequest;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.LoginRequestHandler;
import VJames.Development.NingnenCo_BE.Infrastructure.Presentations.HttpResponse.HttpResponseMapper;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authenticate")
public class AuthController {
    @Autowired
    private LoginRequestHandler loginRequestHandler;
    @Autowired
    private RegisterRequestHandler registerRequestHandler;
    @Autowired
    private ConfirmAccountHandler confirmAccountHandler;
    @Autowired
    private ResendVerifyEmailHandler resendVerifyEmailHandler;
    @Autowired
    private SendResetPasswordCodeHandler sendResetPasswordCodeHandler;
    @Autowired
    private ResetPasswordHandler resetPasswordHandler;
    @Autowired
    private RefreshTokenHandler refreshTokenHandler;
    @Autowired
    private LogoutHandler logoutHandler;

    @Operation(summary = "Endpoint for registering new user")
    @PostMapping("/register")
    private ResponseEntity<Map<String, Object>> register(@RequestBody RegisterUserDto request) {
        RegisterRequest featureRequest = RegisterRequest.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .userRole(request.getUserRole())
                .build();
        Pipeline pipeline = new Pipelinr().with(() -> Stream.of(registerRequestHandler));
        var featureResponse = pipeline.send(featureRequest);
        return HttpResponseMapper.buildResponse(featureResponse.getHttpStatus(), featureResponse);
    }
    @Operation(summary = "Endpoint for login user")
    @PostMapping("/login")
    private ResponseEntity<?> login (@RequestBody LoginUserDto request) {
        LoginRequest featureRequest = LoginRequest.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
        Pipeline pipeline = new Pipelinr().with(() -> Stream.of(loginRequestHandler));
        var featureResponse = pipeline.send(featureRequest);
        return HttpResponseMapper.buildResponse(featureResponse.getHttpStatus(), featureResponse);
    }
    @Operation(summary = "Endpoint for confirming account")
    @GetMapping("/account/confirm")
    private ResponseEntity<Map<String, Object>> confirmAccount(
            @RequestParam("email") String email,
            @RequestParam("verify-token") String verifyToken
    ) {
        ConfirmAccountRequest featureRequest = ConfirmAccountRequest.builder()
                .email(email)
                .verifyToken(verifyToken)
                .build();
        Pipeline pipeline = new Pipelinr().with(() -> Stream.of(confirmAccountHandler));
        var featureResponse = pipeline.send(featureRequest);
        return HttpResponseMapper.buildResponse(featureResponse.getHttpStatus(), featureResponse);
    }
    @Operation(summary = "Endpoint for resending verify email")
    @PatchMapping("/resend-verify-email")
    private ResponseEntity<Map<String, Object>> resendVerifyEmail(@RequestParam("email") String email) {
        ResentVerifyEmailRequest featureRequest = ResentVerifyEmailRequest.builder()
                .email(email)
                .build();
        Pipeline pipeline = new Pipelinr().with(() -> Stream.of(resendVerifyEmailHandler));
        var featureResponse = pipeline.send(featureRequest);
        return HttpResponseMapper.buildResponse(featureResponse.getHttpStatus(), featureResponse);
    }
    @Operation(summary = "Endpoint for sending reset password code")
    @PostMapping("/send-reset-password-code")
    private ResponseEntity<Map<String, Object>> sendResetPasswordCode (@RequestParam("email") String email) {
        SendResetPasswordCodeRequest featureRequest = SendResetPasswordCodeRequest.builder()
                .email(email)
                .build();
        Pipeline pipeline = new Pipelinr().with(() -> Stream.of(sendResetPasswordCodeHandler));
        var featureResponse = pipeline.send(featureRequest);
        return HttpResponseMapper.buildResponse(featureResponse.getHttpStatus(), featureResponse);
    }
    @Operation(summary = "Endpoint for resetting password")
    @PatchMapping("/reset-password")
    private ResponseEntity<Map<String, Object>> resetPassword(@RequestBody ResetPasswordDto request) {
        ResetPasswordRequest featureRequest = ResetPasswordRequest.builder()
                .passCode(request.getPassCode())
                .userName(request.getUserName())
                .newPassword(request.getNewPassword())
                .confirmNewPassword(request.getConfirmNewPassword())
                .build();
        Pipeline pipeline = new Pipelinr().with(() -> Stream.of(resetPasswordHandler));
        var featureResponse = pipeline.send(featureRequest);
        return HttpResponseMapper.buildResponse(featureResponse.getHttpStatus(), featureResponse);
    }
    @Operation(summary = "Endpoint for refreshing access token")
    @PatchMapping("/refresh-access-token")
    private ResponseEntity<Map<String, Object>> refreshAccessToken(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody RefreshAccessTokenDto request
    ) {
        RefreshTokenRequest featureRequest = RefreshTokenRequest.builder()
                .refreshToken(request.getRefreshToken())
                .accessToken(accessToken)
                .build();
        Pipeline pipeline = new Pipelinr().with(() -> Stream.of(refreshTokenHandler));
        var featureResponse = pipeline.send(featureRequest);
        return HttpResponseMapper.buildResponse(featureResponse.getHttpStatus(), featureResponse);
    }
    @Operation(summary = "Endpoint for logout user")
    @PostMapping("/logout")
    private ResponseEntity<Map<String, Object>> logout(@RequestHeader("Authorization") String authToken) {
        LogoutRequest featureRequest = LogoutRequest.builder()
                .authorizationToken(authToken.substring(7))
                .build();
        Pipeline pipeline = new Pipelinr().with(() -> Stream.of(logoutHandler));
        var featureResponse = pipeline.send(featureRequest);
        return HttpResponseMapper.buildResponse(featureResponse.getHttpStatus(), featureResponse);
    }
}
