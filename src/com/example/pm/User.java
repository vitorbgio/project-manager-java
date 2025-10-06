package com.example.pm;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String fullName;
    private String cpf;
    private String email;
    private String position;
    private String login;
    private String password;
    private Role role;

    public User(String fullName, String cpf, String email, String position, String login, String password, Role role) {
        this.id = UUID.randomUUID().toString();
        this.fullName = fullName;
        this.cpf = cpf;
        this.email = email;
        this.position = position;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    // Getters e setters (gerar pelo Eclipse: Source -> Generate Getters and Setters)
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - %s - %s", id, fullName, cpf, email, role);
    }
}
