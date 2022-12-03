package net.backend.questions.softarextask.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.backend.questions.softarextask.dto.UserDto;
import net.backend.questions.softarextask.exception.JwtAuthException;
import net.backend.questions.softarextask.model.Roles;
import net.backend.questions.softarextask.security.utils.JwtAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.token.key.access}")
    private String accessKeyValue;
    @Value("${jwt.token.key.refresh}")
    private String refreshKeyValue;

    private SecretKey getAccessSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.accessKeyValue);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private SecretKey getRefreshSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.refreshKeyValue);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;


    public String generateAccessToken(UserDto user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .claim("roles", getRoleNames(user.getRoles()))
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(this.getAccessSigningKey())
                .compact();
    }

    public String generateRefreshToken(UserDto user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(refreshExpiration)
                .signWith(getRefreshSigningKey())
                .compact();
    }

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(claims.get("roles", List.class));
        jwtInfoToken.setFirstName(claims.get("firstName", String.class));
        jwtInfoToken.setId(claims.get("id", Integer.class));
        jwtInfoToken.setEmail(claims.getSubject());
        return jwtInfoToken;
    }

    public String getSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getAccessSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, this.getAccessSigningKey());
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, this.getRefreshSigningKey());
    }

    private Claims getClaims(@NonNull String token, @NonNull SecretKey secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, this.getAccessSigningKey());
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, this.getRefreshSigningKey());
    }

    public boolean validateToken(String token, SecretKey secretKey) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtAuthException | IllegalArgumentException | ExpiredJwtException e) {
            throw new JwtAuthException("JWT token is expired or invalid");
        }
    }

    private List<String> getRoleNames(Set<Roles> userRoles) {
        return userRoles.stream()
                .map(Roles::getAuthority)
                .toList();
    }
}