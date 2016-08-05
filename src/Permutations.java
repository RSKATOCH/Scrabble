import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Permutations {
	public static Set<String> permutationFinder(String str) {
		Set<String> words = new HashSet<String>();

		for (int i = 0; i < str.length(); i++) {

			words = addLetter(str.charAt(i), words);
		}

		return words;
	}

	private static Set<String> addLetter(char charAt, Set<String> words) {
		Set<String> newWords = new HashSet<String>();
		for (String word : words) {
			for (int i = 0; i < word.length(); i++) {
				StringBuilder str = new StringBuilder(word);
				newWords.add(getSorted(str.insert(i, charAt).toString()));
			}
		}
		newWords.add(String.valueOf(charAt));
		newWords.addAll(words);
		return newWords;
	}

	private static String getSorted(String word) {
		char[] wordArray = word.toCharArray();
		Arrays.sort(wordArray);
		return String.valueOf(wordArray);
	}

}
