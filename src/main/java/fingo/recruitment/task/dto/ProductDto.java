package fingo.recruitment.task.dto;

import fingo.recruitment.task.domain.ProductCategory;

public class ProductDto {

    private Long id;

    private String name;

    private ProductCategory category;

    private Integer number;

    private Boolean bought = Boolean.FALSE;

    //region Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean getBought() {
        return bought;
    }

    public void setBought(Boolean bought) {
        this.bought = bought;
    }

    //endregion
}
