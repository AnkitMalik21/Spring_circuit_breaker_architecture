package com.icwd.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="micro_users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name="ID")
    private String userId;

    @Column(name = "Name", length=20)
    private String name;

    @Column(name="Email")
    private String email;

    @Column(name="About")
    private String about;

    @Transient
    private List<Rating> ratings = new ArrayList<>();

}
