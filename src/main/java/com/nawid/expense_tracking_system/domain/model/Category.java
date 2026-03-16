package com.nawid.expense_tracking_system.domain.model;


import com.nawid.expense_tracking_system.domain.enums.CategoryType;
import jakarta.persistence.*;

@Entity
public class Category extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryType type;

    // getters and setters
}
