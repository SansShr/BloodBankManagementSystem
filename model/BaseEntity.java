package model;

public abstract class BaseEntity {
    private String id;  // Common ID for all entities

    // Default constructor
    public BaseEntity() {}

    // Constructor with ID
    public BaseEntity(String id) {
        this.id = id;
    }

    // Getter and Setter for ID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
