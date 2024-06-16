package com.example.blogapplication.repository;

import com.example.blogapplication.entity.BlogInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogInformationRepository extends JpaRepository<BlogInformation,Integer> {
}
