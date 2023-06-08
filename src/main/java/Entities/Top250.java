package Entities;

import java.sql.ResultSet;
import java.util.Objects;


public class Top250 {
    private int id;
    private String name;

    public Top250(ResultSet resultSet) {
    }

    public Top250() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Top250 top250 = (Top250) o;
        return id == top250.id &&
                Objects.equals(name, top250.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
