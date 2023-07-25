package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Article;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByTitle(String string);


}
