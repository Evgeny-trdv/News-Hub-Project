package com.newshub.NewsHub.repository;

import com.newshub.NewsHub.model.NewsSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsSourceRepository extends JpaRepository<NewsSource, Long> {
}
