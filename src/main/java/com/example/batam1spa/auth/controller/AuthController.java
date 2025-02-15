package com.example.batam1spa.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.batam1spa.auth.dto.AuthResponse;
import com.example.batam1spa.auth.dto.LoginRequest;
import com.example.batam1spa.auth.service.AuthService;
import com.example.batam1spa.common.dto.BaseResponse;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final AuthService authService;

    @Operation(
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "Desmond (Admin)",
                                            value = "{\"username\": \"Desmond123\", \"password\": \"Desmond456\"}"
                                    ),
                                    @ExampleObject(
                                            name = "Jodie (Owner)",
                                            value = "{\"username\": \"Jodie123\", \"password\": \"Jodie456\"}"
                                    ),
                                    @ExampleObject(
                                            name = "Michelle (Manager)",
                                            value = "{\"username\": \"Michelle123\", \"password\": \"Michelle456\"}"
                                    ),
                                    @ExampleObject(
                                            name = "Vanness (Admin)",
                                            value = "{\"username\": \"Vanness123\", \"password\": \"Vanness456\"}"
                                    )
                            }
                    )
            )
    )
    @PostMapping(
            value = "/sign-in",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BaseResponse<AuthResponse>> signIn(@RequestBody LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));

        AuthResponse body = authService.authenticate(request);

        BaseResponse<AuthResponse> response = BaseResponse.success(
                HttpStatus.OK, body, "Authentication successful");

        return ResponseEntity.ok(response);
    }
}