package com.snd.storefinder.services;

import com.snd.storefinder.dtos.Location;
import com.snd.storefinder.dtos.user.UserCreationRequest;
import com.snd.storefinder.dtos.user.UserInfoResponse;
import com.snd.storefinder.exceptions.EmailAlreadyUsedException;
import com.snd.storefinder.exceptions.InvalidUserIdentifierException;
import com.snd.storefinder.models.User;
import com.snd.storefinder.repositories.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final WishlistService wishlistService;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, WishlistService wishlistService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.wishlistService = wishlistService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public UserInfoResponse registerNewUser(@Valid UserCreationRequest request) {
        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new EmailAlreadyUsedException(request.getEmail());
        }
        User user = modelMapper.map(request, User.class);
        userRepository.save(user);
        user.setWishlistId(wishlistService.createWishlist(user));
        return modelMapper.map(user, UserInfoResponse.class);
    }

    public User findUserByIdOrEmail(@Email String email, @PositiveOrZero Integer id) {
        if ((email == null || email.isBlank()) && id == null) {
            throw new InvalidUserIdentifierException("null identifier");
        }
        return (email == null || email.isEmpty())
                ? userRepository.findUserById(id).orElseThrow(() -> new InvalidUserIdentifierException("id:" + id))
                : userRepository.findUserByEmail(email).orElseThrow(() -> new InvalidUserIdentifierException("email:" + email));
    }

    public UserInfoResponse getUserInfoByEmail(@Email String email) {
        return modelMapper.map(findUserByIdOrEmail(email, null), UserInfoResponse.class);
    }

    public UserInfoResponse getUserInfoByUserId(@PositiveOrZero Integer id) {
        return modelMapper.map(findUserByIdOrEmail(null, id), UserInfoResponse.class);
    }

    public UserInfoResponse getUserInfo(@Email String email, @PositiveOrZero Integer id) {
        return modelMapper.map(findUserByIdOrEmail(email, id), UserInfoResponse.class);
    }

    public void validUserId(Integer id) {
        if (userRepository.existsById(id)) {
            return;
        }
        throw new InvalidUserIdentifierException("id:" + id);
    }

    public Map<String, Object> getUserPreferences(@Email String email,
                                                  @PositiveOrZero Integer id) {
        User user = findUserByIdOrEmail(email, id);
        return user.getPreferences();
    }

    @Transactional
    public Map<String, Object> setUserPreferences(@Email String email,
                                      @PositiveOrZero Integer id,
                                      @NotEmpty @NotNull Map<String, Object> preferences) {
        User user = findUserByIdOrEmail(email, id);
        Map<String, Object> existingPreferences = user.getPreferences();
        existingPreferences.putAll(preferences);
        user = userRepository.save(user);
        return user.getPreferences();
    }

    @Transactional
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }



}
