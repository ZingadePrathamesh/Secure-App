package com.secureapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Token {

    @Id
    @GeneratedValue
    private Long id;

    private String token;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    @Column(updatable = false)
    private LocalDateTime expiresAt;
    @Column(insertable = false)
    private LocalDateTime validatedAt;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile userProfile;
}
