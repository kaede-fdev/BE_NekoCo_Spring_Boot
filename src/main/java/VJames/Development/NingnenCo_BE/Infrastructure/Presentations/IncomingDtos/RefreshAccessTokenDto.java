package VJames.Development.NingnenCo_BE.Infrastructure.Presentations.IncomingDtos;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshAccessTokenDto {
    private String refreshToken;
}
