package com.example.pm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Team implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String description;
    private List<String> memberIds = new ArrayList<>(); // armazenamos ids de usuários

    public Team(String name, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<String> getMemberIds() { return memberIds; }
    public void addMember(String userId) { if(!memberIds.contains(userId)) memberIds.add(userId); }
    public void removeMember(String userId) { memberIds.remove(userId); }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (membros: %d)", id, name, description, memberIds.size());
    }
}
