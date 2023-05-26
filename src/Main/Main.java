package Main;

import Database.MyJDBC;

import static Main.MainService.usersTable;

public class Main {
    public static void main(String[] args) {

//        Runtime.getRuntime().addShutdownHook(new Thread(Main::cleanupDatabase));
        Runtime.getRuntime().addShutdownHook(new Thread(Main::closeConnections));
        MainService mainService = new MainService();
    }

    private static void cleanupDatabase() {
        System.out.println("Cleaning up database...");
        MyJDBC databaseService = new MyJDBC();
        databaseService.deleteAll();
    }

    private static void closeConnections() {
        System.out.println("Closing connections...");
        usersTable.setAllUsersOffline();
    }
}