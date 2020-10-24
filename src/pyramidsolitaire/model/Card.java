package cs3500.pyramidsolitaire.model.hw02;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represent a card in a standard 52 card deck in the pyramid solitaire game.
 */
public class Card {
  private Map<Value, Integer> valueMap;
  private final Value value;
  private final Suit suit;

  /**
   * Construct a card.
   *
   * @param value the value of the card(1-13).
   * @param suit  the suit of the card (CLUB'♣', DIAMOND'♦', HEART'♥' ,or SPADE'♠').
   */
  public Card(Value value, Suit suit) {
    this.valueMap = new HashMap<>();
    int cardValue = 1;
    for (Value v : Value.values()) {
      this.valueMap.put(v, cardValue);
      cardValue++;
    }
    this.value = value;
    this.suit = suit;
  }

  /**
   * Check if the given object is the same as this card.
   *
   * @param o the object that this card compares to.
   * @return true if the given object is the same as this card, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return value == card.value
            && suit == card.suit;
  }

  /**
   * Convert the card to a string.
   *
   * @return card string.
   */
  @Override
  public String toString() {
    String suitString;
    switch (this.suit) {
      case HEART:
        suitString = "♥";
        break;
      case DIAMOND:
        suitString = "♦";
        break;
      case SPADE:
        suitString = "♠";
        break;
      default:
        suitString = "♣";
        break;
    }
    return this.valueToString() + suitString;
  }

  /**
   * Get the String corresponding to the value of the card - "A" for 1 - "K" for 13 - "Q" for 12 -
   * "J" for 11.
   *
   * @return String corresponding to the value of the card
   */
  String valueToString() {
    switch (this.value) {
      case ONE:

        return "A";

      case KING:

        return "K";

      case QUEEN:

        return "Q";

      case JACK:

        return "J";

      default:
        return Integer.toString(this.valueMap.get(this.value));
    }
  }

  /**
   * Get the hash code for this card.
   *
   * @return hash code for this card.
   */
  @Override
  public int hashCode() {
    return Objects.hash(value, suit);
  }

  /**
   * Get the value.
   *
   * @return the value.
   */
  public int getValue() {
    return this.valueMap.get(this.value);
  }

  /**
   * Get the suit.
   *
   * @return the suit.
   */
  public Suit getSuit() {
    return suit;
  }
}
