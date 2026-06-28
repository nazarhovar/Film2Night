package com.example.film2night.entity;

import javax.persistence.*;

@Entity
@Table(name = "top250")
public class Top250Film {
    @Id
    private Integer id;
    @Column(nullable = false)
    private String name;

    public Top250Film() {}
    public Top250Film(Integer id, String name) { this.id = id; this.name = name; }

    public Integer getId() { return id; }
    public String getName() { return name; }
}
