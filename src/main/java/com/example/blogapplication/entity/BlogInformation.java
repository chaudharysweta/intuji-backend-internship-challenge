package com.example.blogapplication.entity;

import com.example.blogapplication.enums.Categories;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blog_app",uniqueConstraints = {
        @UniqueConstraint(name = "uk_blog_info", columnNames = "title")
})
public class BlogInformation {

    @Id
    @SequenceGenerator(name = "blog_info_gen",sequenceName = "basic_info_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="blog_info_gen" )
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "category",nullable = false,length = 100)
    @Enumerated(EnumType.STRING)
    private Categories categories;
}
