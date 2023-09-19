package com.assistant.savedocument.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

/**
 * Created by Semih, 3.07.2023
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private String username;

    private String fileName;

    private String fileType;

    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(length = 16000000)
    private byte[] data;

    public LocalDateTime time;

}
