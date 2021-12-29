package tvg;

public class ConsoleUI {

    public static final String END_TURN = "To end your turn";

    public static void printModeSelection() {
        System.out.println("Press 1 - Game short version 10 rounds");
        System.out.println("Press 2 - Game long version until only one player alive");
    }

    public static void printBuyOption() {
        System.out.println("Press 1 - To buy this trap");
        System.out.println("Press 2 - " + END_TURN);
    }

    public static void printUpgradeOption() {
        System.out.println("Press 1 - To upgrade this trap");
        System.out.println("Press 2 - " + END_TURN);
    }

    public static void printStealOption() {
        System.out.println("Press 1 - To steal this trap");
        System.out.println("Press 2 - " + END_TURN);
    }

    public static void printPassTurn() {
        System.out.println("Press 1 - " + END_TURN);
    }
}
