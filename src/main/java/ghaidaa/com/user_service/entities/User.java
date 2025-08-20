package ghaidaa.com.user_service.entities;

import ghaidaa.com.user_service.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users" , indexes = {
@Index(name="ix_users_email", columnList="email", unique = true),
@Index(name="ix_users_phone", columnList="phone", unique = true),
@Index(name="ix_users_national_id", columnList="national_id", unique = true)
       })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "full_name",nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 15)
    private String phone;

    @Column(nullable = false, unique = true, length = 14)
    private String nationalId;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;  // CITIZEN / ADMIN

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt ;

    @PrePersist
    void onCreate() { this.createdAt = LocalDateTime.now(); }

//    @PreUpdate
//    void onUpdate() { this.updatedAt = LocalDateTime.now(); }
}
