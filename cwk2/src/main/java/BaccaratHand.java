// TODO: Implement the BaccaratHand class in the file
class BaccaratHand extends CardCollection {
    public BaccaratHand() {
        super(); // cards = new LinkedList<>();
    }

    public int value() {
        int sumOfCards = 0;

        for (int i = 0; i < size(); ++i) {
            sumOfCards += cards.get(i).value();
        }

        if (sumOfCards >= 10) {
            sumOfCards = sumOfCards % 10;
        }
        return sumOfCards;
    }

    public boolean isNatural() {
        int value = value();

        if (value == 8 || value == 9 && (size() == 2 || size() == 3)) {
            return true;
        }
        return false;
    }

    public Card get(int index) {
        return cards.get(index);
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < size(); ++i) {
            result += get(i).toString();
            if (i != size() - 1) {
                result += " ";
            }
        }
        return result;
    }
}