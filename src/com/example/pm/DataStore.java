package com.example.pm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class DataStore {
    public static List<User> users = new ArrayList<>();
    public static List<Project> projects = new ArrayList<>();
    public static List<Team> teams = new ArrayList<>();

    private static final String USERS_FILE = "users.dat";
    private static final String PROJECTS_FILE = "projects.dat";
    private static final String TEAMS_FILE = "teams.dat";

    public static void saveAll() {
        save(USERS_FILE, users);
        save(PROJECTS_FILE, projects);
        save(TEAMS_FILE, teams);
    }

    private static void save(String filename, Object obj) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(obj);
        } catch (Exception e) {
            System.out.println("Erro ao salvar " + filename + ": " + e.getMessage());
        }
    }

    public static void loadAll() {
        Object u = load(USERS_FILE);
        if (u != null) users = (List<User>) u;
        Object p = load(PROJECTS_FILE);
        if (p != null) projects = (List<Project>) p;
        Object t = load(TEAMS_FILE);
        if (t != null) teams = (List<Team>) t;
    }

    private static Object load(String filename) {
        File f = new File(filename);
        if (!f.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return ois.readObject();
        } catch (Exception e) {
            System.out.println("Erro ao carregar " + filename + ": " + e.getMessage());
            return null;
        }
    }
}
