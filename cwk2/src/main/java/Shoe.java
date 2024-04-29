import java.util.Collections;
import java.util.List;
import java.util.Arrays;

// TODO: Implement the Shoe class in this file
class Shoe extends CardCollection {
    public Shoe(int decks) {
        super();
        if (decks != 6 && decks != 8) {
            throw new CardException("You have to have either 6 or 8 decks!");
        }

        // Code to create a deck
        for (int i = 0; i < decks; ++i) {
            for (int j = 0; j < 4; ++j) {

                String cardSuit = "";
                switch (j) {
                    case 0:
                        cardSuit = "Clubs";
                        break;
                    case 1:
                        cardSuit = "Diamonds";
                        break;
                    case 2:
                        cardSuit = "Hearts";
                        break;
                    case 3:
                        cardSuit = "Spades";
                        break;
                }

                String aceCard = String.format("Ace of %s", cardSuit);

                add(new BaccaratCard(aceCard));

                // For loop to create all number cards

                List<String> numList = Arrays.asList("TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE",
                        "TEN");

                for (int cardNum = 2; cardNum < 11; ++cardNum) {
                    String temp = String.format("%s of %s", numList.get(cardNum - 2), cardSuit);
                    add(new BaccaratCard(temp));
                }

                // Create Jack, Queen and King

                String jackCard = String.format("Jack of %s", cardSuit);
                add(new BaccaratCard(jackCard));
                String queenCard = String.format("Queen of %s", cardSuit);
                add(new BaccaratCard(queenCard));
                String kingCard = String.format("King of %s", cardSuit);
                add(new BaccaratCard(kingCard));

            }

        }

        //
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card deal() {
        if (isEmpty()) {
            throw new CardException("You can't deal cards from an empty deck!");
        }

        Card card = cards.get(0);
        cards.remove(0);

        return card;
    }

}
