package org.launchcode.models;

/**
 * Created by LaunchCode
 */
// enum that enables view and controller layer to easily ask for data relaged to
// a specific job field or to all fields
public enum JobFieldType {

    EMPLOYER ("Employer"),
    LOCATION ("Location"),
    CORE_COMPETENCY ("Skill"),
    POSITION_TYPE ("Position Type"),
    ALL ("All");

    private final String name;

    JobFieldType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
