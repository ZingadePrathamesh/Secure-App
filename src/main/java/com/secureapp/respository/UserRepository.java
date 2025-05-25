package com.secureapp.respository;

import com.secureapp.model.UserProfile;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long>, JpaSpecificationExecutor<UserProfile> {
    Optional<UserProfile> findByEmail(@NonNull String username);

    @Override
    @NonNull
    Page<UserProfile> findAll(Specification spec, @NonNull Pageable pageable);
}
