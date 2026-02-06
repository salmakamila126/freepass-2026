 package com.bcc.canteen.controller;

 import org.springframework.http.ResponseEntity;
 import org.springframework.security.access.prepost.PreAuthorize;
 import org.springframework.web.bind.annotation.*;
 import com.bcc.canteen.entity.User;
 import com.bcc.canteen.entity.Role;
 import com.bcc.canteen.repository.UserRepository;
 import com.bcc.canteen.security.JwtUtil;
 import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 import com.bcc.canteen.service.UserService;
 import com.bcc.canteen.dto.UserResponse;
 import com.bcc.canteen.dto.LoginRequest;
 import com.bcc.canteen.dto.AuthResponse;

 @RestController
  @RequestMapping("/auth")
  public class AuthController {

      private final UserRepository userRepository;
      private final JwtUtil jwtUtil;
      private final BCryptPasswordEncoder passwordEncoder;
      private final UserService userService;

      public AuthController(UserRepository userRepository, JwtUtil jwtUtil, UserService userService, BCryptPasswordEncoder passwordEncoder) {
          this.userRepository = userRepository;
          this.jwtUtil = jwtUtil;
          this.userService = userService;
          this.passwordEncoder = passwordEncoder;
      }

     @GetMapping("/admin/owner")
     @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
     public ResponseEntity<String> adminOwnerEndpoint() {
         return ResponseEntity.ok("Hanya Admin atau Owner yang bisa akses ini!");
     }

     @GetMapping("/admin")
     @PreAuthorize("hasRole('ADMIN')")
     public ResponseEntity<String> adminOnly() {
         return ResponseEntity.ok("Hanya Admin");
     }

     @GetMapping("/owner")
     @PreAuthorize("hasRole('OWNER')")
     public ResponseEntity<String> ownerOnly() {
         return ResponseEntity.ok("Hanya Owner");
     }

     @PostMapping("/register")
     public UserResponse register(@RequestBody User user) {
          user.setPassword(passwordEncoder.encode(user.getPassword()));
          user.setRole(Role.USER);
          User savedUser = userRepository.save(user);

          return userService.mapToDTO(savedUser);
     }

     @PostMapping("/login")
     public AuthResponse login(@RequestBody LoginRequest request) {

         User user = userRepository.findByUsername(request.getUsername())
                 .orElseThrow(() -> new RuntimeException("User not found"));

         if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
             throw new RuntimeException("Invalid password");
         }

         String token = jwtUtil.generateToken(
                 user.getId(),
                 user.getUsername(),
                 user.getRole().name()
         );

         return new AuthResponse(token);
     }

     @PostMapping("/admin/owner")
      public UserResponse addOwner(@RequestBody User owner) {
          owner.setRole(Role.OWNER);
          User savedOwner = userRepository.save(owner);

          return userService.mapToDTO(savedOwner);
      }

      @PutMapping("/admin/owner/{ownerId}")
      public UserResponse editOwner(@PathVariable Long ownerId, @RequestBody User ownerDetails) {
          User owner = userRepository.findById(ownerId)
                  .orElseThrow(() -> new RuntimeException("Owner not found"));
          owner.setName(ownerDetails.getName());
          owner.setEmail(ownerDetails.getEmail());
          owner.setUsername(ownerDetails.getUsername());

          return userService.mapToDTO(userRepository.save(owner));
      }

     @PutMapping("/profile/{userId}")
     public UserResponse updateProfile(@PathVariable Long userId, @RequestBody User userDetails) {
         User user = userRepository.findById(userId)
                 .orElseThrow(() -> new RuntimeException("User not found"));

         user.setName(userDetails.getName());
         user.setEmail(userDetails.getEmail());
         user.setUsername(userDetails.getUsername());

         return userService.mapToDTO(userRepository.save(user));
     }
 }
