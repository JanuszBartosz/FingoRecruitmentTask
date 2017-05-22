package fingo.recruitment.task.dto;

import fingo.recruitment.task.domain.ProductCategory;

import java.util.Objects;

public class ProductDto {

    private Long id;

    private String name;

    private ProductCategory category;

    private Integer number;

    private Boolean bought;

    public ProductDto() {
        this.bought = Boolean.FALSE;
    }

    public ProductDto(String name, ProductCategory category, Integer number) {
        this.name = name;
        this.category = category;
        this.number = number;
        this.bought = Boolean.FALSE;
    }

    public ProductDto(Long id, String name, ProductCategory category, Integer number, Boolean bought) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.number = number;
        this.bought = bought;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof ProductDto)) return false;
        ProductDto productDto = (ProductDto) o;
        if (Objects.equals(this.getId(),productDto.getId()) &&
                Objects.equals(this.getName(), productDto.getName()) &&
                Objects.equals(this.getCategory(), productDto.getCategory()) &&
                Objects.equals(this.getBought(), productDto.getBought())) {
            return true;
        } else
            return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.name, this.category, this.number, this.bought);
    }
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
