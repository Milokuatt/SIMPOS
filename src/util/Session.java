package simpos.util;

import simpos.model.Pengguna;

public class Session {
    private static Pengguna currentUser;

    public static void setCurrentUser(Pengguna user) { currentUser = user; }
    public static Pengguna getCurrentUser() { return currentUser; }
    public static void logout() { currentUser = null; }
    public static boolean isLoggedIn() { return currentUser != null; }
}
