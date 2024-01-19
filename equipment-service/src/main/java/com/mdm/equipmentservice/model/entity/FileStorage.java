package com.mdm.equipmentservice.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "file_storage")
@Builder
@AllArgsConstructor
public class FileStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private String associatedEntityType;

    @Column(nullable = false)
    private Long associatedEntityId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FileDescription description;

    @Lob
    @Column(nullable = false, length = 100000)
    private byte[] data;

}
