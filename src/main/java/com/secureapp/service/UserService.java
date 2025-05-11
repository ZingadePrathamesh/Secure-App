package com.secureapp.service;

import com.secureapp.exceptions.UserNotFoundException;
import com.secureapp.model.UserProfile;
import com.secureapp.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserProfile getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User does not exists with id : " + id));
    }

    public UserProfile getByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User does not exists with username : " + email));
    }

    public List<UserProfile> getAllUsers() {
        return userRepository.findAll();
    }

    public UserProfile saveStudent(UserProfile userProfile){
        return userRepository.save(userProfile);
    }

    public void updateStudent(UserProfile newUserProfile){
        UserProfile oldUserProfile = getById(newUserProfile.getId());
        oldUserProfile.setEmail(newUserProfile.getEmail());
    }

    public void deleteStudent(Long studentId){
        userRepository.deleteById(studentId);
    }


}
