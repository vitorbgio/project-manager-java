package com.example.pm;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ProjectManagerApp {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        DataStore.loadAll();
        seedIfEmpty();
        while (true) {
            showMenu();
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1": createUser(); break;
                case "2": listUsers(); break;
                case "3": createTeam(); break;
                case "4": listTeams(); break;
                case "5": createProject(); break;
                case "6": listProjects(); break;
                case "7": assignTeamToProject(); break;
                case "8": reportProjects(); break;
                case "9": DataStore.saveAll(); System.out.println("Salvo. Saindo."); System.exit(0); break;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("=== Project Manager ===");
        System.out.println("1) Cadastrar usuário");
        System.out.println("2) Listar usuários");
        System.out.println("3) Cadastrar equipe");
        System.out.println("4) Listar equipes");
        System.out.println("5) Cadastrar projeto");
        System.out.println("6) Listar projetos");
        System.out.println("7) Alocar equipe a projeto");
        System.out.println("8) Relatório: projetos");
        System.out.println("9) Salvar e sair");
        System.out.print("Escolha: ");
    }

    private static void createUser() {
        System.out.print("Nome completo: "); String nome = sc.nextLine();
        System.out.print("CPF: "); String cpf = sc.nextLine();
        System.out.print("E-mail: "); String email = sc.nextLine();
        System.out.print("Cargo: "); String cargo = sc.nextLine();
        System.out.print("Login: "); String login = sc.nextLine();
        System.out.print("Senha: "); String senha = sc.nextLine();
        System.out.print("Perfil (ADMIN, MANAGER, COLLABORATOR): "); String p = sc.nextLine();
        Role role;
        try { role = Role.valueOf(p.toUpperCase()); }
        catch(Exception e) { System.out.println("Perfil inválido. Usando COLLABORATOR."); role = Role.COLLABORATOR; }
        User u = new User(nome, cpf, email, cargo, login, senha, role);
        DataStore.users.add(u);
        DataStore.saveAll();
        System.out.println("Usuário criado: " + u);
    }

    private static void listUsers() {
        List<User> users = DataStore.users;
        if (users.isEmpty()) System.out.println("Nenhum usuário cadastrado.");
        else users.forEach(System.out::println);
    }

    private static void createTeam() {
        System.out.print("Nome da equipe: "); String name = sc.nextLine();
        System.out.print("Descrição: "); String desc = sc.nextLine();
        Team t = new Team(name, desc);
        DataStore.teams.add(t);
        DataStore.saveAll();
        System.out.println("Equipe criada: " + t);
    }

    private static void listTeams() {
        List<Team> teams = DataStore.teams;
        if (teams.isEmpty()) System.out.println("Nenhuma equipe.");
        else teams.forEach(System.out::println);
    }

    private static void createProject() {
        System.out.print("Nome do projeto: "); String name = sc.nextLine();
        System.out.print("Descrição: "); String desc = sc.nextLine();
        System.out.print("Data inicio (YYYY-MM-DD): "); String sd = sc.nextLine();
        System.out.print("Data término prevista (YYYY-MM-DD): "); String ed = sc.nextLine();
        LocalDate start = null, end = null;
        try { start = LocalDate.parse(sd); end = LocalDate.parse(ed); }
        catch(DateTimeParseException e) { System.out.println("Formato de data inválido. Use YYYY-MM-DD."); return; }
        System.out.print("Status (PLANNED, IN_PROGRESS, COMPLETED, CANCELED): "); String st = sc.nextLine();
        ProjectStatus status;
        try { status = ProjectStatus.valueOf(st.toUpperCase()); } catch (Exception e) { status = ProjectStatus.PLANNED; }
        System.out.print("Informe o id do gerente (login): "); String managerLogin = sc.nextLine();
        // buscamos usuário por login
        String managerId = null;
        for (User u : DataStore.users) if (u.getLogin().equals(managerLogin)) { managerId = u.getId(); break; }
        if (managerId == null) { System.out.println("Gerente não encontrado (use login). Crie o usuário primeiro."); return; }
        Project p = new Project(name, desc, start, end, status, managerId);
        DataStore.projects.add(p);
        DataStore.saveAll();
        System.out.println("Projeto criado: " + p);
    }

    private static void listProjects() {
        List<Project> projects = DataStore.projects;
        if (projects.isEmpty()) System.out.println("Nenhum projeto.");
        else {
            for (Project p : projects) {
                System.out.println(p);
                // mostrar gerente
                User g = DataStore.users.stream().filter(u -> u.getId().equals(p.getManagerId())).findFirst().orElse(null);
                System.out.println("  Gerente: " + (g != null ? g.getFullName() + " ("+g.getLogin()+")" : "—"));
                // mostrar equipes
                if (p.getTeamIds().isEmpty()) System.out.println("  Equipes: (nenhuma)");
                else {
                    System.out.print("  Equipes: ");
                    p.getTeamIds().forEach(tid -> {
                        Team t = DataStore.teams.stream().filter(tt -> tt.getId().equals(tid)).findFirst().orElse(null);
                        if (t != null) System.out.print(t.getName() + " ");
                    });
                    System.out.println();
                }
            }
        }
    }

    private static void assignTeamToProject() {
        System.out.print("Informe o id do projeto: "); String pid = sc.nextLine();
        System.out.print("Informe o id da equipe: "); String tid = sc.nextLine();
        Project project = DataStore.projects.stream().filter(p -> p.getId().equals(pid)).findFirst().orElse(null);
        Team team = DataStore.teams.stream().filter(t -> t.getId().equals(tid)).findFirst().orElse(null);
        if (project == null) { System.out.println("Projeto não encontrado."); return; }
        if (team == null) { System.out.println("Equipe não encontrada."); return; }
        project.addTeam(tid);
        DataStore.saveAll();
        System.out.println("Equipe adicionada ao projeto.");
    }

    private static void reportProjects() {
        System.out.println("Relatório rápido de projetos:");
        for (Project p : DataStore.projects) {
            User g = DataStore.users.stream().filter(u -> u.getId().equals(p.getManagerId())).findFirst().orElse(null);
            System.out.printf("%s - %s - %s - Gerente: %s - Equipes: %d%n", p.getId(), p.getName(), p.getStatus(), (g != null ? g.getFullName() : "—"), p.getTeamIds().size());
        }
    }

    private static void seedIfEmpty() {
        if (DataStore.users.isEmpty()) {
            User admin = new User("Admin", "00000000000", "admin@local", "Admin", "admin", "admin", Role.ADMIN);
            DataStore.users.add(admin);
            DataStore.saveAll();
        }
    }
}
