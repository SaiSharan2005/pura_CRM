package com.crm.springbootjwtimplementation.domain;


// import com.fasterxml.jackson.annotation.JsonBackReference;
// import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "name",unique = true,nullable = false)
    private String name;


    @Override
    public String toString() {
        return "Role{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                '}';
    }
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

}



// INSERT INTO roles (id, name) VALUES
// (1, 'ADMIN'),
// (2, 'SALESMAN'),
// (3, 'CUSTOMER'),
// (4, 'DELIVERY');
