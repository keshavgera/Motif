package models;

import java.io.Serializable;


public class UploadRequirementBrokerPojo implements Serializable {

    private String name;
    private String mobileNo;
    private String dateOfShifting;
    private String typeOfProperty;
    private String furnishing;
    private String rooms;
    private String specification;
    private String preferredLocation;
    private String budgetFrom;
    private String budgetTo;
    private String distanceFromOffice;
    private String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDateOfShifting() {
        return dateOfShifting;
    }

    public void setDateOfShifting(String dateOfShifting) {
        this.dateOfShifting = dateOfShifting;
    }

    public String getTypeOfProperty() {
        return typeOfProperty;
    }

    public void setTypeOfProperty(String typeOfProperty) {
        this.typeOfProperty = typeOfProperty;
    }

    public String getFurnishing() {
        return furnishing;
    }

    public void setFurnishing(String furnishing) {
        this.furnishing = furnishing;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getPreferredLocation() {
        return preferredLocation;
    }

    public void setPreferredLocation(String preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    public String getBudgetFrom() {
        return budgetFrom;
    }

    public void setBudgetFrom(String budgetFrom) {
        this.budgetFrom = budgetFrom;
    }

    public String getBudgetTo() {
        return budgetTo;
    }

    public void setBudgetTo(String budgetTo) {
        this.budgetTo = budgetTo;
    }

    public String getDistanceFromOffice() {
        return distanceFromOffice;
    }

    public void setDistanceFromOffice(String distanceFromOffice) {
        this.distanceFromOffice = distanceFromOffice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
