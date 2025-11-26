package com.example.services;

import com.example.Config.AuthConfig;
import com.example.Infrastructure.Persistence.UserRepository;
import com.example.model.User;
import com.example.web.dto.RegisterRequest;
import com.example.web.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;
    private final Random random = new Random();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, StringRedisTemplate redisTemplate) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }


    @Transactional
    public User registerUser(String phone, String password) {
        Optional<User> existing = userRepository.findByPhone(phone);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("phone already registered");
        }
        String hashPassword = hashPassword(password);
        User user = new User(phone,hashPassword);
        user.setId(UUID.randomUUID());
        user.setPhone(phone);
        user.setPasswordHash(hashPassword);
        user.setPhoneVerified(true); //for now i am skipping the otp verification feature as that is not free to send SMS

        // createdAt will be set by @PrePersist in entity, but persist here
        User saved = userRepository.save(user);

        // generate OTP and store in Redis (best-effort; if redis unavailable we still proceed)
        //**********SKIP for now**********
//        String otp = generateOtp();
//        try {
//            if (redisTemplate != null) {
//                String key = "otp:" + phone;
//                redisTemplate.opsForValue().set(key, otp, Duration.ofMinutes(5));
//            }
//            // Log simulated OTP for development/debug — do NOT log OTPs in production
//            log.info("Simulated OTP for phone {} : {}", phone, otp);
//        } catch (Exception e) {
//            log.warn("Failed to store OTP in Redis (continuing): {}", e.getMessage());
//        }

        return saved;
    }

    public UserDTO getUserById(String userId){
        UUID id = UUID.fromString(userId);
        return userRepository.getUserById(id);
    }

    public List<String> getAllUsers() {
        System.out.println(userRepository.findAllPhoneNumbers());
        return userRepository.findAllPhoneNumbers();
    }

    //Verify OTP stored in Redis (returns true if matches and deletes the OTP).
    public boolean verifyOtp(String phone, String otp) {
        if (otp == null || phone == null) return false;
        try {
            String key = "otp:" + phone;
            String stored = redisTemplate.opsForValue().get(key);
            if (stored != null && stored.equals(otp)) {
                // consume OTP
                redisTemplate.delete(key);
                return true;
            }
        } catch (Exception e) {
            log.warn("OTP verify error (redis): {}", e.getMessage());
        }
        return false;
    }

    private String generateOtp() {
        int v = 100000 + random.nextInt(900000);
        return Integer.toString(v);
    }

    public String hashPassword(String Password) {
        return passwordEncoder.encode(Password);
    }

    public User authenticateUser(String phone, String password) {
        Optional<User> userOpt = userRepository.findByPhone(phone);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("invalid phone or password");
        }
        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new IllegalArgumentException("invalid phone or password");
        }
        return user;
    }

}