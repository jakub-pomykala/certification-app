package io.openliberty.exam.rest.model;

public class SystemData {

    private int id;
    private final String name;

    public SystemData(String name) {
        this.name = name;
    }

    public SystemData(int id, String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String name() {
        return name;
    }
}