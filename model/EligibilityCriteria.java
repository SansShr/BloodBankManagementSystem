package model;

public class EligibilityCriteria {
    private String bloodType;
    private String criteria;

    public EligibilityCriteria(String bloodType, String criteria) {
        this.bloodType = bloodType;
        this.criteria = criteria;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
}
