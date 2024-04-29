import java.util.Scanner;

public class Baccarat {
  // Defining class attributes
  boolean interact;
  Shoe shoe;
  int round;
  BaccaratHand player;
  BaccaratHand banker;
  boolean game;
  int playerWins;
  int bankerWins;
  int ties;

  public Baccarat() {

  }

  public Baccarat(String i) {
    shoe = new Shoe(6);
    round = 0;
    player = new BaccaratHand();
    banker = new BaccaratHand();
    game = true;
    if (i.equals("-i") || i.equals("--interactive")) {
      interact = true;
    } else {
      interact = false;
    }
    playerWins = 0;
    bankerWins = 0;
    ties = 0;
  }

  public static void main(String[] args) {
    Baccarat baccaratInstance;

    // Creates new instance of baccarat game to actually run the game in, with
    // attributes instantiated.
    if (args.length != 0) {
      baccaratInstance = new Baccarat(args[0]);
    } else {
      baccaratInstance = new Baccarat("");
    }

    baccaratInstance.baccaratGame();

    return;
  }

  public void baccaratGame() {
    while (game == true) {
      // Increment round
      round += 1;
      System.out.printf("Round %d\n", round);
      // Clear hands
      player.discard();
      banker.discard();
      // Deal hands
      dealHand();
      // Print the state of the game
      printGameState();
      // If both players don't get a natural hand, perform the third card protocol
      String temp;
      if (player.isNatural() == false && banker.isNatural() == false) {
        temp = thirdCardCheck();
        if (temp != "") { // Prints out third card status if third cards have been dealt
          System.out.printf("Dealing third card to %s...\n", temp);
        }
        printGameState();
      } else {
        temp = "";
      }

      // Prints who the winner of the round is
      switch (determineWinner()) {
        case 'p':
          playerWins += 1;
          System.out.println("Player wins!\n");
          break;
        case 'b':
          bankerWins += 1;
          System.out.println("Banker wins!\n");
          break;
        case 't':
          ties += 1;
          System.out.println("Tie!\n");
          break;
        case 'e':
          throw new CardException("There has been an error winner detection\n");
      }
      // Checks for round continuation
      if (shoe.size() >= 6) { // Checks size of shoe. If less than 6 end game.
        if (interact == true) { // Checks for interact mode. If it is then ask for the user's option.
          if (continueOption() == false) { // If the user doesn't want to continue, end while loop.
            flipGameStatus();
          } else { // User indicated they wanted to continue
            continue;
          }

        } else { // Interactive mode is off, so continue anyway
          continue;
        }

      } else {
        flipGameStatus(); // Size of shoe too small, end while loop.
      }

    }

    // Print scores at the end
    System.out.printf("%d rounds played\n%d player wins\n%d banker wins\n%d ties", round, playerWins, bankerWins, ties);

    return;
  }

  public void printGameState() {
    // Prints the game
    System.out.printf("Player: %s = %d\nBanker: %s = %d\n", player.toString(), player.value(),
        banker.toString(), banker.value());
    return;
  }

  public char determineWinner() {
    // Determines who won the game
    if (player.isNatural() && banker.isNatural()) { // if both players have natural hands - tie
      return 't';
    } else if (player.isNatural() && !banker.isNatural()) { // if player has a natural hand and banker doesn't - player
      return 'p';
    } else if (!player.isNatural() && banker.isNatural()) { // if banker has natural hand and player doesn't - banker
      return 'b';
    } else if (player.value() == banker.value()) { // if player and banker have equal hand values - tie
      return 't';
    } else if (player.value() > banker.value()) { // player hand value is greater than banker hand value - player
      return 'p';
    } else if (banker.value() > player.value()) { // banker hand value is greater than player hand value - banker
      return 'b';
    } else {
      return 'e';
    }

  }

  public String thirdCardCheck() {
    boolean playerThirdCard;
    boolean bankerThirdCard;

    // Player's rule
    if (player.value() <= 5) {
      playerThirdCard = true;
      dealPlayerCard();

    } else {
      playerThirdCard = false;

    }

    // Banker's rule
    if (!playerThirdCard) {
      if (banker.value() <= 5) {
        bankerThirdCard = true;
        dealBankerCard();
      } else {
        bankerThirdCard = false;
      }

    } else {
      if (banker.value() <= 2) {
        bankerThirdCard = true;
        dealBankerCard();
      } else if (banker.value() == 3 && getThirdCard() != 8) {
        bankerThirdCard = true;
        dealBankerCard();
      } else if (banker.value() == 4 && getThirdCard() >= 2 && getThirdCard() <= 7) {
        bankerThirdCard = true;
        dealBankerCard();
      } else if (banker.value() == 5 && getThirdCard() >= 4 && getThirdCard() <= 7) {
        bankerThirdCard = true;
        dealBankerCard();
      } else if (banker.value() == 6 && getThirdCard() >= 6 && getThirdCard() <= 7) {
        bankerThirdCard = true;
        dealBankerCard();
      } else {
        bankerThirdCard = false;
      }
    }

    if (!playerThirdCard && !bankerThirdCard) {
      return "";
    } else if (playerThirdCard && !bankerThirdCard) {
      return "player";
    } else if (!playerThirdCard && bankerThirdCard) {
      return "banker";
    } else {
      return "player and banker";
    }

  }

  public void flipGameStatus() {
    game = !(game);
  }

  public void dealBankerCard() {
    banker.add(shoe.deal());
    return;
  }

  public void dealPlayerCard() {
    player.add(shoe.deal());
    return;
  }

  public void dealHand() {
    // Deals cards at the start of each round to player and banker
    dealPlayerCard();
    dealPlayerCard();
    dealBankerCard();
    dealBankerCard();
  }

  public int getThirdCard() {
    // This gets the rank of the third card and converts it to an integer
    return Integer.parseInt(String.valueOf(player.get(2).getRank().getSymbol()));
  }

  public boolean continueOption() {
    // Scans in the user's input
    Scanner optionScanner = new Scanner(System.in);
    System.out.printf("Another round? (y/n): ");
    Character option;

    if (optionScanner.hasNext()) {
      option = Character.toUpperCase(optionScanner.next().charAt(0)); // Gets first character
    } else {
      option = ' ';
    }

    if (option.equals('Y')) {
      return true;
    }
    optionScanner.close();
    return false;
  }

}
