package com.example.streamsschool.adapters;

public class classItem {
    String section, branch, year;


    public classItem(String section, String branch, String year){
        this.section = section;
        this.branch = branch;
        this.year = year;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
