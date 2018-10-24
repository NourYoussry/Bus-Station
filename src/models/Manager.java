package models;

public class Manager {
    private String managerName ;
    private String managerCode;
    private String managerAccessibility;

    public Manager(){

    };

    public Manager(String managerName, String managerCode, String managerAccessibility) {
        this.managerName = managerName;
        this.managerCode = managerCode;
        this.managerAccessibility = managerAccessibility;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(String managerCode) {
        this.managerCode = managerCode;
    }

    public String getManagerAccessibility() {
        return managerAccessibility;
    }

    public void setManagerAccessibility(String managerAccessibility) {
        this.managerAccessibility = managerAccessibility;
    }
}
