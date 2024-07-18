package com.mehmetnuri.entity;


import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;

import java.time.LocalDateTime;

@MongoEntity(collection = "users")
public class User extends ReactivePanacheMongoEntity {

    private String name;
    private int age;
    private String email;
    private String address;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }

    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
