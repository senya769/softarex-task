package net.backend.questions.softarextask.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import net.backend.questions.softarextask.dto.UserDto;
import net.backend.questions.softarextask.exception.JwtAuthException;
import net.backend.questions.softarextask.model.Roles;
import net.backend.questions.softarextask.model.utils.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
@Autowired
  private   UserDetailsService userDetails;

    private final SecretKey secret = Keys.hmacShaKeyFor("accessToken-accessToken-accessToken-7".getBytes());

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;


//    public JwtTokenProvider( ) {
//        String encode = Encoders.BASE64.encode("accessacceessacseeesasasasc12313123123123123".getBytes());
//        this.secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(encode));
//    }




    public String createToken(UserDto user, List<Roles> roles) {
        // add me custom params in token
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("roles", getRoleNames(roles));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        // generation token
        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(secret,SignatureAlgorithm.HS256)//
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetails.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setFirstName(claims.get("firstName", String.class));
        jwtInfoToken.setEmail(claims.getSubject());
        return jwtInfoToken;
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(secret).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    // check on valid token
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secret).build()
                    .parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthException("JWT token is expired or invalid");
        }
    }

    private Set<String> getRoleNames(List<Roles> userRoles) {
        Set<String> result = new HashSet<>();

        userRoles.forEach(role ->
            result.add(role.getAuthority())
        );

        return result;
    }
    private static Set<Roles> getRoles(Claims claims) {
        final List<String> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(Roles::valueOf)
                .collect(Collectors.toSet());
    }

}