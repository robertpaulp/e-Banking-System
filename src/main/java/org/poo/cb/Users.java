package org.poo.cb;


import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Users {

    // Using Singleton Design Pattern
    private static Users instance;

    private final HashMap<String, User> users = new HashMap<>();
    private final HashMap<String, String> friends = new HashMap<>();

    public Users() {
    }

    public static Users getInstance() {
        if (instance == null) {
            instance = new Users();
        }
        return instance;
    }

    public void addUser(@NotNull User user) throws Exceptions {
        if (users.containsKey(user.email)) {
            throw new Exceptions.UserAlreadyExists("User with " + user.getEmail() + " already exists\n");
        }
        users.put(user.email, user);
    }

    public void addFriend(@NotNull String email1, @NotNull String email2) throws Exceptions {
        if (!users.containsKey(email1)) {
            throw new Exceptions.UserDoesntExist("User with " + email1 + " doesn't exist\n");
        }
        if (!users.containsKey(email2)) {
            throw new Exceptions.UserDoesntExist("User with " + email2 + " doesn't exist\n");
        }
        if (friends.containsKey(email1)) {
            throw new Exceptions.UserIsAlreadyFriend("User with " + email2 + " is already a friend\n");
        }
        friends.put(email1, email2);
    }

    public String listUser(@NotNull String email) throws Exceptions {
        if (!users.containsKey(email)) {
            throw new Exceptions.UserDoesntExist("User with " + email + " doesn't exist\n");
        }
        User user = users.get(email);
        String output = "{\"email\":\"" + user.getEmail() + "\"," +
                "\"firstname\":\"" + user.getNume() + "\"," +
                "\"lastname\":\"" + user.getPrenume() + "\"," +
                "\"address\":\"" + user.getAddress() + "\"," +
                "\"friends\":[";
        if (friends.containsKey(email)) {
            output += "\"" + friends.get(email) + "\"";
        }
        output += "]}\n";

        return output;
    }

    public User getUser(@NotNull String email) throws Exceptions {
        if (!users.containsKey(email)) {
            throw new Exceptions.UserDoesntExist("User with " + email + " doesn't exist\n");
        }
        return users.get(email);
    }

    public void resetData() {
        users.clear();
        friends.clear();
    }

    public static class User {
        private String email;
        private final String nume;
        private final String prenume;
        private final String address;
        private final Portofolio portofolio;
        private boolean statusPremium = false;

        public User(String email, String nume, String prenume, String address) {
            this.email = email;
            this.nume = nume;
            this.prenume = prenume;
            this.address = address;
            this.portofolio = new Portofolio();
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNume() {
            return nume;
        }

        public String getPrenume() {
            return prenume;
        }

        public String getAddress() {
            return address;
        }

        public Portofolio getPortofolio() {
            return portofolio;
        }

        public boolean getStatusPremium() {
            return statusPremium;
        }

        public void setStatusPremium(boolean statusPremium) {
            this.statusPremium = statusPremium;
        }
    }
}
