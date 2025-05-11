package com.secureapp.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.secureapp.enums.Permission.*;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    ADMIN(Set.of(
            ADMIN_GET,
            ADMIN_POST,
            ADMIN_PUT,
            ADMIN_DELETE,

            STUDENT_GET,
            STUDENT_POST,
            STUDENT_PUT,
            STUDENT_DELETE,

            PRINCIPAL_GET,
            PRINCIPAL_POST,
            PRINCIPAL_PUT,
            PRINCIPAL_DELETE,

            TEACHER_GET,
            TEACHER_POST,
            TEACHER_PUT,
            TEACHER_DELETE
    )),
    STUDENT(Set.of(
            STUDENT_GET,
            STUDENT_POST,
            STUDENT_PUT,
            STUDENT_DELETE
    )),
    PRINCIPAL(Set.of(
            PRINCIPAL_GET,
            PRINCIPAL_POST,
            PRINCIPAL_PUT,
            PRINCIPAL_DELETE

    )),
    TEACHER(Set.of(
            TEACHER_GET,
            TEACHER_POST,
            TEACHER_PUT,
            TEACHER_DELETE
    ));

    @Override
    public String getAuthority() {
        return this.name();
    }

    private final Set<Permission> permissions;

    public Collection<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = this.permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
