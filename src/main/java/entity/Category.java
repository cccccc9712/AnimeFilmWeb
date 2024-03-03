package entity;

public class Category {
    private String categoryID;
    private String categoryName;

    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryID() { return categoryID; }

    public void setCategoryID(String categoryID) { this.categoryID = categoryID;}

    @Override
    public String toString() {
        return "Category{" +
                "categoryID='" + categoryID + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
