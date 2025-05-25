package com.secureapp.filters;

import com.secureapp.dto.UserFilter;
import com.secureapp.enums.Role;
import com.secureapp.model.UserProfile;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<UserProfile> dynamicFiltering(UserFilter userFilter){
      return (root, query, criteriaBuilder) -> {
          List<Predicate> predicates = new ArrayList<>();

          if (userFilter.getFirstName() != null && !userFilter.getFirstName().isBlank()) {
              predicates.add(criteriaBuilder.like(
                      criteriaBuilder.lower(root.get("firstName")),
                      "%" + userFilter.getFirstName().toLowerCase() + "%"
              ));
          }

          if (userFilter.getLastName() != null && !userFilter.getLastName().isBlank()) {
              predicates.add(criteriaBuilder.like(
                      criteriaBuilder.lower(root.get("lastName")),
                      "%" + userFilter.getLastName().toLowerCase() + "%"
              ));
          }

          if (userFilter.getEmail() != null && !userFilter.getEmail().isBlank()) {
              predicates.add(criteriaBuilder.like(
                      criteriaBuilder.lower(root.get("email")),
                      "%" + userFilter.getEmail().toLowerCase() + "%"
              ));
          }

          if (userFilter.getRole() != null && !userFilter.getRole().isBlank()) {
              predicates.add(criteriaBuilder.equal(
                      root.get("role"),
                      Role.valueOf(userFilter.getRole().toUpperCase())
              ));
          }

          // Always apply the "enabled" filter
          predicates.add(criteriaBuilder.equal(
                  root.get("enabled"),
                  userFilter.isEnable()
          ));

          return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      };
    }
}
