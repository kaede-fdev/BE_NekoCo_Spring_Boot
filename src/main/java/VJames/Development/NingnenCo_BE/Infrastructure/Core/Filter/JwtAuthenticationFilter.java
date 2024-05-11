package VJames.Development.NingnenCo_BE.Infrastructure.Core.Filter;

import VJames.Development.NingnenCo_BE.Domain.Entities.Token;
import VJames.Development.NingnenCo_BE.Domain.UnitOfWork.IUnitOfWork;
import VJames.Development.NingnenCo_BE.Infrastructure.Core.IdentifyService.JWT.IJwtService;
import VJames.Development.NingnenCo_BE.Infrastructure.Core.IdentifyService.JWT.JwtServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final IJwtService _jwt;
    private final UserDetailsService userDetailsService;
    private final IUnitOfWork _unitOfWork;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
    {
        try {
            String authHeader = request.getHeader("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = authHeader.substring(7);
            String username = _jwt.extractUserName(token);
            String rToken = _jwt.extractRefreshToken(token);
            //todo checking valid token
            //todo fetch token from redis
            //...
            Token foundToken = _unitOfWork.getTokenRepository().findByRefreshToken(rToken);
            if(foundToken == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                System.out.println(LocalDateTime.now() + "  Can't authenticating 'cause by logout or refreshed!");
            }
            if(username != null && foundToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(_jwt.isValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                filterChain.doFilter(request, response);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
