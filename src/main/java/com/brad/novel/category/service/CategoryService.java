package com.brad.novel.category.service;

import com.brad.novel.category.entity.Category;
import com.brad.novel.category.exception.CategoryNotFoundException;
import com.brad.novel.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("찾는 카테고리가 없습니다."));
    }

    /* 최대한 간단하게 기능 구현, API 로 사용하지 않는 내용. */
    public Long save(String name) {
        Category category = new Category();
        category.addName(name);
        categoryRepository.save(category);
        return category.getId();
    }
}
