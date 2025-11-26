package com.example.web;

import java.util.List;
import java.util.Map;

import com.example.model.User;
import com.example.security.TokenService;
import com.example.services.UserService;
import com.example.web.dto.ApiResponse;
import com.example.web.dto.RegisterRequest;
import com.example.web.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/User")
public class AuthController {
    private final UserService userService;
    private final TokenService tokenService;
    private static final String COOKIE_NAME = "token";

    public AuthController(UserService userService,TokenService tokenService) {
        this.userService=userService;
        this.tokenService=tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest req) {
        try{
            User user = userService.registerUser(req.getPhone(),req.getPassword());
            String token = tokenService.issueToken(user.getId().toString());
            ResponseCookie cookie = ResponseCookie.from("token",token)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Strict")
                    .path("/")
                    .maxAge(7*24*60*60)
                    .build();
            ApiResponse response = new ApiResponse("success","Registration Succcessful");
            return  ResponseEntity
                    .ok()
                    .header("Set-Cookie",cookie.toString())
                    .body(response);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new ApiResponse("error",e.getMessage()));
        }
        catch(Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse("error", "registration failed"));
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<String> users;
        try{
            users = userService.getAllUsers();
        }
        catch(Exception e){
            return ResponseEntity.internalServerError().body(new ApiResponse("error","error in fetching all users"));
        }
        return ResponseEntity.ok(new ApiResponse("success","data : "+users));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody RegisterRequest req) {
        try{
            User user = userService.authenticateUser(req.getPhone(),req.getPassword());
            String token = tokenService.issueToken(user.getId().toString());
            ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME,token)
                    .httpOnly(true)
                    .secure(false)
                    .sameSite("None")
                    .path("/")
                    .maxAge(7*24*60*60)
                    .build();
            ApiResponse response = new ApiResponse("success","Login Successful");
            return  ResponseEntity
                    .ok()
                    .header("Set-Cookie",cookie.toString())
                    .body(response);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("error",e.getMessage()));
        }
        catch(Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse("error", "login failed"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("None")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("status", "logged_out"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> currentUser(HttpServletRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth==null || auth.getPrincipal()==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user not authenticated");
        }
        String userId = auth.getPrincipal().toString();
        UserDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
}
