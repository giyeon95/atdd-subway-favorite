package nextstep.auth.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextstep.auth.context.Authentication;
import nextstep.auth.token.JwtTokenProvider;
import nextstep.auth.token.TokenResponse;
import org.springframework.http.MediaType;

public class NewTokenAuthenticationInterceptor extends AuthenticationInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final TokenAuthenticationConverter tokenAuthenticationConverter;

    public NewTokenAuthenticationInterceptor(
        UserDetailsService userDetailsService,
        AuthenticationConverter authenticationConverter,
        JwtTokenProvider jwtTokenProvider, ObjectMapper objectMapper,
        TokenAuthenticationConverter tokenAuthenticationConverter) {
        super(userDetailsService, authenticationConverter);
        this.jwtTokenProvider = jwtTokenProvider;
        this.objectMapper = objectMapper;
        this.tokenAuthenticationConverter = tokenAuthenticationConverter;
    }

    @Override
    public void afterAuthentication(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        String payload = objectMapper.writeValueAsString(authentication.getPrincipal());
        String accessToken = jwtTokenProvider.createToken(payload);
        TokenResponse tokenResponse = new TokenResponse(accessToken);

        String responseToClient = objectMapper.writeValueAsString(tokenResponse);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().print(responseToClient);
    }
}
