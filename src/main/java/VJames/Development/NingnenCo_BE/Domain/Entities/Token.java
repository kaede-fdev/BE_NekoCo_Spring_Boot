package VJames.Development.NingnenCo_BE.Domain.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "token_type")
    private String tokenType;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "refresh_token_expiration_time")
    private LocalDateTime refreshTokenExpirationTime;
}
