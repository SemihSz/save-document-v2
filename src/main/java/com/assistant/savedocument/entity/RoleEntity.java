package com.assistant.savedocument.entity;

import com.assistant.savedocument.model.enums.RoleTypes;
import jakarta.persistence.*;
import lombok.*;


/**
 * Created by Semih, 2.07.2023
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleTypes name;

}
