package com.crm.springbootjwtimplementation.domain;

// import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor()
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Long id;

        @Size(min = 6, message = "Username length must be minimum 6")
        @Column(name = "username", unique = true)
        private String username;

        @Email(message = "Email Should Be Valid")
        @Column(name = "email", unique = true, nullable = false)
        private String email;

        @JsonIgnore
        @Size(min = 8, message = "Password length must be minimum 8")
        @Column(name = "password")
        private String password;

        @Column(name = "image_url")
        private String imageUrl;
    
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
        @JsonIgnore
        private Set<Role> roles = new HashSet<>();

        @Override
        public String toString() {
                return "User{" +
                                "id='" + getId() + '\'' +
                                "username='" + username + '\'' +
                                ", email='" + email + '\'' +
                                ", password='" + password + '\'' +
                                '}';
        }
}
