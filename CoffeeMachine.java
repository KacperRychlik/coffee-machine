package machine;
import java.util.Scanner;
import static machine.States.*;


public class CoffeeMachine {
    static Scanner scanner = new Scanner(System.in);
    /*
     * index 0 is water
     * index 1 is milk
     * index 2 is coffee beans
     * index 3 is cups
     * index 4 is money
     */
    static int[] supplies = {400, 540, 120, 9, 550};
    static final int[] espressoCost = {-250, -0, -16, -1, 4};
    static final int[] latteCost = {-350, -75, -20, -1, 7};
    static final int[] cappuccinoCost = {-200, -100, -12, -1, 6};
    static boolean exit = false;
    static States state;
    static String action;
    static String drinkNumber;
    static int addedSupplies;

    public static void main(String[] args) {
        while (!exit) {
            state = ACTION;
            System.out.print("\nWrite action (buy, fill, take, remaining, exit):\n> ");
            input(scanner.next());
            switch (action) {
                case "buy":
                    state = BUY;
                    System.out.print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:\n> ");
                    input(scanner.next());
                    switch (drinkNumber) {
                        case "1":
                            makeCoffee(supplies, espressoCost);
                            break;
                        case "2":
                            makeCoffee(supplies, latteCost);
                            break;
                        case "3":
                            makeCoffee(supplies, cappuccinoCost);
                            break;
                        case "back":
                            break;
                        default:
                            System.out.println("ERROR: Unknown drink");
                    }
                    break;
                case "fill":
                    state = FILL;
                    fill(supplies);
                    break;
                case "take":
                    System.out.printf("I gave you $%d\n", supplies[4]);
                    supplies[4] = 0;
                    break;
                case "remaining":
                    printSupplies(supplies);
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("ERROR: Unknown action");
            }
            System.out.println();
        }
    }

    public static void input(String input){
        switch (state){
            case ACTION:
                action = input;
                break;
            case BUY:
                drinkNumber = input;
                break;
            case FILL:
                addedSupplies = Integer.parseInt(input);
        }
    }

    public static void printSupplies(int[] supplies){
        System.out.println("The coffee machine has:");
        System.out.printf("%d of water\n", supplies[0]);
        System.out.printf("%d of milk\n", supplies[1]);
        System.out.printf("%d of coffee beans\n", supplies[2]);
        System.out.printf("%d of disposable cups\n", supplies[3]);
        System.out.printf("%d of money\n", supplies[4]);
    }

    public static boolean hasEnough(int[] supplies, int[] drink){
        int[] output = new int[5];
        for(int i = 0; i < 5; i++){
            output[i] = supplies[i] + drink[i];
            if(output[i] < 0){
                return false;
            }
        }
        return true;
    }

    public static void makeCoffee(int[] supplies, int[] drink){
        if(hasEnough(supplies, drink)){
            for(int i = 0; i < 5; i++){
                supplies[i] += drink[i];
            }
        }
    }

    public static void fill(int[] supplies){
        String[] massage = {"Write how many ml of water do you want to add:\n> ",
                "Write how many ml of milk do you want to add:\n> ",
                "Write how many grams of coffee beans do you want to add:\n> ",
                "Write how many disposable cups of coffee do you want to add:\n> "};
        for(int i = 0; i < 4; i++){
            System.out.print(massage[i]);
            input(scanner.next());
            supplies[i] += addedSupplies;
        }
    }
}

