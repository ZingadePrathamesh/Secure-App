package com.secureapp.service;

import com.secureapp.dto.UserDTO;
import com.secureapp.dto.UserFilter;
import com.secureapp.exceptions.UserNotFoundException;
import com.secureapp.filters.UserSpecification;
import com.secureapp.mapper.UserMapper;
import com.secureapp.model.UserProfile;
import com.secureapp.respository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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

    public Page<UserDTO> getAllUsers(UserFilter userFilter){
        int pageNumber = userFilter.getPage();
        int size = userFilter.getSize() == 0 ? 10 : userFilter.getSize();
        Sort sort = Sort.by("firstName", "lastName");
        Pageable page = PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.ASC, "firstName", "lastName"));
        return UserMapper.toUserDTOs(userRepository.findAll(
                UserSpecification.dynamicFiltering(userFilter),
                page
        ));
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
