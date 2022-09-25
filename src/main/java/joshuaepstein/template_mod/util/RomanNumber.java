package joshuaepstein.template_mod.util;

import java.util.TreeMap;

public class RomanNumber {
  private static final TreeMap<Integer, String> LITERALS = new TreeMap<>();

  public static String toRoman(int number) {
    if (number == 0)
      return "";
    int literal = LITERALS.floorKey(number);
    if (number == literal)
      return LITERALS.get(number);
    return LITERALS.get(literal) + toRoman(number - literal);
  }

  static {
    LITERALS.put(1000, "M");
    LITERALS.put(900, "CM");
    LITERALS.put(500, "D");
    LITERALS.put(400, "CD");
    LITERALS.put(100, "C");
    LITERALS.put(90, "XC");
    LITERALS.put(50, "L");
    LITERALS.put(40, "XL");
    LITERALS.put(10, "X");
    LITERALS.put(9, "IX");
    LITERALS.put(5, "V");
    LITERALS.put(4, "IV");
    LITERALS.put(1, "I");
  }
}
