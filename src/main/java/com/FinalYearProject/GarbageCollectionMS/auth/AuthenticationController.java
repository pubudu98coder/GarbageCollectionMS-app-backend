package com.FinalYearProject.GarbageCollectionMS.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value ="/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000",allowCredentials ="true",allowedHeaders = "*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping(value = "/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest requestR){
        return  ResponseEntity.ok(authenticationService.register(requestR));
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest requestA){
        return  ResponseEntity.ok(authenticationService.authenticate(requestA));
    }

    @PostMapping(value = "/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request,response);
    }
}
