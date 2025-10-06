package com.example.pm;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;
    private String managerId;
    private List<String> teamIds = new ArrayList<>();

    public Project(String name, String description, LocalDate startDate, LocalDate endDate, ProjectStatus status, String managerId) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.managerId = managerId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public ProjectStatus getStatus() { return status; }
    public void setStatus(ProjectStatus status) { this.status = status; }
    public String getManagerId() { return managerId; }
    public List<String> getTeamIds() { return teamIds; }
    public void addTeam(String teamId) { if(!teamIds.contains(teamId)) teamIds.add(teamId); }
    public void removeTeam(String teamId) { teamIds.remove(teamId); }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (%s -> %s) - %s", id, name, description, startDate, endDate, status);
    }
}
