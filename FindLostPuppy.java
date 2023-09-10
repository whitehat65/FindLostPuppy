import java.util.Scanner;
import java.util.Random;

public class FindLostPuppy {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        System.out.print("Enter the number of floors: ");
        int numFloors = scanner.nextInt();

        System.out.print("Enter the number of rooms: ");
        int numRooms = scanner.nextInt();

        char[][] building = new char[numFloors][numRooms];

        for (int i = 0; i < numFloors; i++) {
            for (int j = 0; j < numRooms; j++) {
                building[i][j] = ' ';
            }
        }

        int[] player1 = placeRandomly(building, random);
        int[] player2;

        do {
            player2 = placeRandomly(building, random);
        } while (player2[0] == player1[0] && player2[1] == player1[1]);

        int[] object;

        do {
            object = placeRandomly(building, random);
        } while ((object[0] == player1[0] && object[1] == player1[1]) ||
                (object[0] == player2[0] && object[1] == player2[1]));

        building[object[0]][object[1]] = 'P';

        boolean gameWon = false;

        while (!gameWon) {
            printBuildingStrucure(building, player1, player2, object);

            System.out.println("Player 1's turn:");
            System.out.print("Enter a move (U/D/L/R): ");
            String move1 = scanner.next().toUpperCase();
            try {
                movePlayer(building, player1, move1, '1', numFloors,numRooms);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            if (player1[0] == object[0] && player1[1] == object[1]) {
                System.out.println("Congratulations, Player 1 found the puppy!");
                gameWon = true;
                break;
            }

            printBuildingStrucure(building, player1, player2, object);

            System.out.println("Player 2's turn:");
            System.out.print("Enter a move (U/D/L/R): ");
            String move2 = scanner.next().toUpperCase();
            try {
                movePlayer(building, player2, move2, '2', numFloors,numRooms);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            if (player2[0] == object[0] && player2[1] == object[1]) {
                System.out.println("Congratulations, Player 2 found the puppy!");
                gameWon = true;
                break;
            }
        }
        scanner.close();
    }

    public static int[] placeRandomly(char[][] building, Random random) {
        int[] position = new int[2];
        int numberOfFloors = building.length;
        int numberOfRooms = building[0].length;

        do {
            position[0] = random.nextInt(numberOfFloors);
            position[1] = random.nextInt(numberOfRooms);
        } while (building[position[0]][position[1]] != ' ');

        return position;
    }

    public static void movePlayer(char[][] building, int[] currentPosition, String move, char player, int numFloors,int numRooms) {
        int[] newPosition = new int[2];

        switch (move) {
        
        case "U":
	        if (currentPosition[0] > 0) {
	            newPosition[0] = currentPosition[0] - 1;
	            newPosition[1] = currentPosition[1];
	        } else {
	            throw new IllegalArgumentException("You are already on the top floor!");
	        }
	        break;
        case "D":
            if (currentPosition[0] < numFloors - 1) {
                newPosition[0] = currentPosition[0] + 1;
                newPosition[1] = currentPosition[1];
            } else {
                throw new IllegalArgumentException("You are already on the ground floor!");
            }
            break;
        case "L":
            if (currentPosition[1] > 0) {
                newPosition[0] = currentPosition[0];
                newPosition[1] = currentPosition[1] - 1;
            } else {
                throw new IllegalArgumentException("You are already at the leftmost position!");
            }
            break;
        case "R":
        	if (currentPosition[1] < numRooms-1) {
                newPosition[0] = currentPosition[0];
                newPosition[1] = currentPosition[1] + 1;
            } else {
                throw new IllegalArgumentException("You are already at the rightmost position!");
            }
            break;
        default:
            return;
    }


        if (isValidMove(building, newPosition)) {
            building[currentPosition[0]][currentPosition[1]] = ' ';
            building[newPosition[0]][newPosition[1]] = player;
            currentPosition[0] = newPosition[0];
            currentPosition[1] = newPosition[1];
        }
    }

    public static boolean isValidMove(char[][] building, int[] position) {
        int numberOfFloors = building.length;
        int numberOfRooms = building[0].length;
        int floor = position[0];
        int room = position[1];

        return floor >= 0 && floor < numberOfFloors && room >= 0 && room < numberOfRooms && building[floor][room] != '1' && building[floor][room] != '2';
    }

    public static void printBuildingStrucure(char[][] building, int[] player1, int[] player2, int[] object) {
        System.out.println("Current state of the game:");

        for (int i = 0; i < building.length; i++) {
            for (int j = 0; j < building[i].length; j++) {
                System.out.print("+--");
            }
            System.out.println("+");

            for (int j = 0; j < building[i].length; j++) {
                if (i == player1[0] && j == player1[1]) {
                    System.out.print("|1 ");
                } else if (i == player2[0] && j == player2[1]) {
                    System.out.print("|2 ");
                } else if (i == object[0] && j == object[1]) {
                    System.out.print("|P ");
                } else {
                    System.out.print("|  ");
                }
            }
            System.out.println("|");
        }

        for (int j = 0; j < building[0].length; j++) {
            System.out.print("+--");
        }
        System.out.println("+");
    }
}