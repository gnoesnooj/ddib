package com.ddib.user.entity;

//import com.ssafy.stocker.company.entity.UserCompanyEntity;
//import com.ssafy.stocker.company.entity.UserCompanyEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "user")
@Getter
@Setter
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String username;
    private String name;

    private String email;

    private String role;



//    @OneToMany(mappedBy = "user")
//    private Set<UserCompanyEntity> userCompanyEntities = new HashSet<>();
}
