import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Scrabble {

	String letters;
	final String BLANK = "&";

	private String getSorted(String word) {
		char[] wordArray = word.toCharArray();
		Arrays.sort(wordArray);
		return String.valueOf(wordArray);
	}

	public Set<String> getWordSet(ArrayList<String> wordsList) {
		Set<String> wordSet = new HashSet<String>();
		for (String word : wordsList) {
			wordSet.add(getSorted(word));
		}
		return wordSet;
	}

	public Set<String> readFile(String path) throws Exception {
		return getWordSet((ArrayList<String>) Files.readAllLines(Paths.get(path)));
	}

	public boolean isValid(String s, Set<String> dict, int numOfBlanks) {
		String regstr = s.replaceAll("", ".*");
		for (String word : dict) {
			if (word.matches(regstr) && (word.length() - s.length() == numOfBlanks)) {
				return true;
			}
		}

		return false;
	}

	public int scoreLetter(char c) {
		switch (c) {
		case 'A':
		case 'E':
		case 'I':
		case 'O':
		case 'U':
		case 'L':
		case 'N':
		case 'R':
		case 'S':
		case 'T':
			return 1;
		case 'D':
		case 'G':
			return 2;
		case 'B':
		case 'C':
		case 'M':
		case 'P':
			return 3;
		case 'F':
		case 'H':
		case 'W':
		case 'Y':
		case 'V':
			return 4;
		case 'K':
			return 5;
		case 'J':
		case 'X':
			return 8;
		case 'Z':
		case 'Q':
			return 10;
		default:
			return 0;
		}

	}

	public int totalScore(String s) {
		int score = 0;
		for (int i = 0; i < s.length(); i++)
			score += scoreLetter(s.charAt(i));
		return score;
	}

	public int getMaxScore(String str, Set<String> dictionary) {
		int maxScore = 0;
		String sortedStrWithoutBlank = removeBlanksSorted(str);
		Set<String> permutations = Permutations.permutationFinder(sortedStrWithoutBlank);
		int numOfBlanks = str.length() - sortedStrWithoutBlank.length();

		for (String permWord : permutations) {
			if (isValid(permWord, dictionary, numOfBlanks) && maxScore < totalScore(permWord)) {
				maxScore = totalScore(permWord);
			}
		}
		return maxScore;
	}

	private String removeBlanksSorted(String str) {
		return getSorted(str.replaceAll(BLANK, ""));
	}

	public static void main(String args[]) throws Exception {
		Scrabble sc = new Scrabble();
		String filePath = args[0];
		String input = args[1];
		Set<String> dictionary = sc.readFile(filePath);
		System.out.println(sc.getMaxScore(input, dictionary));
	}
}
