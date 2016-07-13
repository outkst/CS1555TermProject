/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facespace;

import java.util.Scanner;

/**
 *
 * @author kylemonto
 */
public class FaceSpace {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to FaceSpaces!");
        String input = "";
        while (!input.equals("Q")) {
            System.out.println("What would you like to do? (Enter q to exit)\n"
                    + "A - Create a User \t\t\t H - Send Group a Message \n"
                    + "B - Initiate a friendship \t\t I - Display Messages for User \n"
                    + "C - Establish a friendship \t\t J - Display New Messages for User\n"
                    + "D - Display User's friends \t\t K - Search for User\n"
                    + "E - Create a Group \t\t\t L - Three Degrees Between Users\n"
                    + "F - Add User to Group \t\t\t M - Top Messagers\n"
                    + "G - Send User a Message \t\t N - Drop a User\n");
            Scanner keyIn = new Scanner(System.in);
            input = keyIn.next().toUpperCase();
            switch (input) {
                case "A":
                    System.out.println("createUser");
//                createUser();
                    break;
                case "B":
                    System.out.println("initiateFriendship");
//                initiateFriendship();
                    break;
                case "C":
                    System.out.println("establishFriendship");
//                establishFriendship();
                    break;
                case "D":
                    System.out.println("displayFriends");
//                displayFriends();
                    break;
                case "E":
                    System.out.println("createGroup");
//                createGroup();
                    break;
                case "F":
                    System.out.println("addToGroup");
//                addToGroup();
                    break;
                case "G":
                    System.out.println("establishFriendship");
//                sendMessageToUser();
                    break;
                case "H":
                    System.out.println("sendMessageToGroup");
//                sendMessageToGroup();
                    break;
                case "I":
                    System.out.println("displayMessages");
//                displayMessages();
                    break;
                case "J":
                    System.out.println("displayNewMessages");
//                displayNewMessages();
                    break;
                case "K":
                    System.out.println("searchForUser");
//                searchForUser();
                    break;
                case "L":
                    System.out.println("threeDegrees");
//                threeDegrees();
                    break;
                case "M":
                    System.out.println("topMessagers");
//                topMessagers();
                    break;
                case "N":
                    System.out.println("dropUser");
//                dropUser();
                    break;
            }
        }
    }

}
