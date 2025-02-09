package com.snd.storefinder.controllers;

import com.snd.storefinder.dtos.user.UserCreationRequest;
import com.snd.storefinder.dtos.user.UserInfoResponse;
import com.snd.storefinder.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<UserInfoResponse> registerUser(@RequestBody UserCreationRequest request) {
        UserInfoResponse response = userService.registerNewUser(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestParam(value = "id", required = false) Integer id,
                                                        @RequestParam(value = "email", required = false) String email) {
        return ResponseEntity.ok(userService.getUserInfo(email, id));
    }

    @GetMapping("/pref")
    public Map<String, Object> getUserPref(@RequestParam(value = "id", required = false) Integer id,
                                           @RequestParam(value = "email", required = false) String email) {
        return userService.getUserPreferences(email, id);
    }

    @PostMapping("/pref")
    public Map<String, Object> setUserPref(@RequestParam(value = "id", required = false) Integer id,
                                           @RequestParam(value = "email", required = false) String email,
                                           @RequestBody Map<String, Object> pref) {
        return userService.setUserPreferences(email, id, pref);
    }
}
