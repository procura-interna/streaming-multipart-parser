package dev.baecher;

import java.util.Arrays;

public final class BmhSearch {

  private BmhSearch() {
  }


  /**
   * Searches for the first occurrence of a byte pattern in a byte array. Uses the
   * Boyer–Moore–Horspool algorithm for fast average-case byte pattern searching.
   */
  public static int search(final byte[] reference, final int referenceStart, final int referenceEnd,
      final byte[] toSearch) {
    if (toSearch.length == 0) {
      return 0;
    }

    final int[] bmhPattern = preprocessBmhPattern(toSearch);

    final int referenceLength = referenceEnd - referenceStart;


    int skip = referenceStart;
    while (referenceLength - skip >= toSearch.length) {
      if (bmhSame(reference, skip, referenceEnd, toSearch)) {
        return skip;
      }

      skip = skip + bmhPattern[reference[skip + toSearch.length - 1]  & 0xFF];
    }

    return -1;
  }

  private static boolean bmhSame(final byte[] reference, final int referenceStart,
      final int referenceEnd, final byte[] toSearch) {

    if (referenceStart + toSearch.length > referenceEnd) {
      return false;
    }

    for (int i = toSearch.length - 1; i >= 0; i--) {
      if (reference[referenceStart + i] != toSearch[i]) {
        return false;
      }
    }

    return true;
  }

  private static int[] preprocessBmhPattern(final byte[] toSearch) {
    final int bmhPatternLength = 256;
    final int[] bmhPattern = new int[bmhPatternLength];

    Arrays.fill(bmhPattern, toSearch.length);

    for (int i = 0; i < toSearch.length - 1; i++) {
      bmhPattern[toSearch[i] & 0xFF] = toSearch.length - 1 - i;
    }

    return bmhPattern;
  }
}
