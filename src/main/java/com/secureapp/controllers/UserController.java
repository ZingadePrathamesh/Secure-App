package com.secureapp.controllers;

import com.secureapp.dto.UserDTO;
import com.secureapp.dto.UserFilter;
import com.secureapp.model.UserProfile;
import com.secureapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("all")
    public ResponseEntity<List<UserProfile>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("user-id/{user-id}")
    public ResponseEntity<UserProfile> getStudentById(
            @PathVariable(name = "user-id") Long id
    ){
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/user-id/{user-id}/filter")
    public ResponseEntity<Page<UserDTO>> getUserByFilter(
            @ModelAttribute UserFilter userFilter
            ){
        return ResponseEntity.ok(userService.getAllUsers(userFilter));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("user-id/{user-id}/delete/{delete-id}")
    public ResponseEntity deleteUser(
            @PathVariable("delete-id") Long id
    ){
        userService.deleteStudent(id);
        return ResponseEntity.accepted().build();
    }

}
