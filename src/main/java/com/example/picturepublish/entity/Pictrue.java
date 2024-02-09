package com.example.picturepublish.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "pictures")
public class Pictrue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String category;
    private String fileurl;
    @ManyToOne
    @JoinColumn(name = "userInfo", referencedColumnName = "id")
    @JsonManagedReference(value = "id")
    private UserInfo userInfo;
    private String status;
}
