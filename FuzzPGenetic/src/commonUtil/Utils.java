package commonUtil;

public final class Utils {

  public static String firstCompletePairOfBrackets(String what, char startBracker, char endBracket) {
    if (what.charAt(0) != startBracker) {
      throw new RuntimeException("Pharsing error at " + what);
    }
    int brackectCntr = 1;
    int index = 1;
    while ((index < what.length()) && (brackectCntr != 0)) {
      brackectCntr += (what.charAt(index) == startBracker) ? 1 : 0;
      brackectCntr -= (what.charAt(index) == endBracket) ? 1 : 0;
      index++;
    }
    if (index == what.length()) {
      throw new RuntimeException("Pharsing error at " + what + "\n incositent braces");
    }

    return what.substring(0, index);
  }

  public static String getFirstSubstringUntilSpace(String what) {
    int index = 0;
    while ((index < what.length()) && (what.charAt(index) != ' '))
      index++;
    return what.substring(0, index);
  }

  public static String[] splitToChildren(String what) {
    String whatTrimed = what.trim().replaceAll(", ", ",");
    String first = null;
    if (whatTrimed.startsWith("(")) {
      first = firstCompletePairOfBrackets(whatTrimed, '(', ')');
    } else {
      first = getFirstSubstringUntilSpace(whatTrimed);
    }
    String second = whatTrimed.substring(first.length() + 1).trim();
    return (new String[] { first, second });

  }

}
