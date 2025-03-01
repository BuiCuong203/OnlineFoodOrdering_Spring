package com.FoodOrder.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.FoodOrder.config.JwtProvider;
import com.FoodOrder.model.Cart;
import com.FoodOrder.model.InvalidatedToken;
import com.FoodOrder.model.USER_ROLE;
import com.FoodOrder.model.User;
import com.FoodOrder.repository.CartRepository;
import com.FoodOrder.repository.InvalidatedTokenRepository;
import com.FoodOrder.repository.UserRepository;
import com.FoodOrder.request.LoginRequest;
import com.FoodOrder.response.AuthResponse;
import com.FoodOrder.response.MessageResponse;
import com.FoodOrder.service.CustomerUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${jwt.signerKey}")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist != null) {
            throw new Exception("Email is already used with another account");
        }

        User createdUser = User.builder()
                .email(user.getEmail())
                .fullname(user.getFullname())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(user.getRole())
                .build();
        User savedUser = userRepository.save(createdUser);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = AuthResponse.builder()
                .jwt(jwt)
                .message("Register success")
                .role(savedUser.getRole())
                .build();

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest request) {
        String username = request.getEmail();
        String password = request.getPassword();

        Authentication authentication = authenticate(username, password);

        String jwt = jwtProvider.generateToken(authentication);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role =
                authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        AuthResponse authResponse = AuthResponse.builder()
                .jwt(jwt)
                .message("Login success")
                .role(USER_ROLE.valueOf(role))
                .build();

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/signout")
    public ResponseEntity<MessageResponse> logout(@RequestHeader("Authorization") String jwt) {
        jwt = jwt.substring(7);
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Claims claims =
                Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        Date expiryTime = claims.getExpiration();

        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().token(jwt).expiryTime(expiryTime).build();
        invalidatedTokenRepository.save(invalidatedToken);

        MessageResponse response =
                MessageResponse.builder().message("Signout success").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username......");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password......");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
