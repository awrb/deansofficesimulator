package uam.aleksy.deansoffice.tour.consequences.enums;

public enum ConsequenceType {
    BEER("beers to drink"),
    FIRE_EMPLOYEE("fire an employee"),
    EXTRA_DIFFERENTIAL("extra differential"),
    COMPLAIN("complain in a bus"),
    EXTRA_TASKS("extra tasks"),
    PUNISH_STUDENT("punish student");

    private String value;

    public String getValue() {
        return value;
    }

    ConsequenceType(String value) {
        this.value = value;
    }
}
