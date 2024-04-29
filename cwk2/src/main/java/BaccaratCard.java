// TODO: Implement the BaccaratCard class in this file
class BaccaratCard extends Card {
    public BaccaratCard(Rank r, Suit s) {
        super(r, s);
    }

    public BaccaratCard(String name) {
        super(name);
    }

    public int value() {
        int value = super.value();
        if (value == 10) {
            value = 0;
        }

        return value;
    }
}