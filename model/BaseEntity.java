package model;

public abstract class BaseEntity {
    private String id;  // Common ID for all entities

    // Getter and Setter for ID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
