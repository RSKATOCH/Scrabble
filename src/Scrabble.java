import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Scrabble {

	String wordMatch;
	final String BLANK = "&";

	private String getSorted(String word) {
		char[] wordArray = word.toUpperCase().toCharArray();
		Arrays.sort(wordArray);
		return String.valueOf(wordArray);
	}

	public Map<String, String> getWordMap(ArrayList<String> wordsList, int frontSpaces, int blankSpaces, char letter) {
		Map<String, String> wordSet = new HashMap<String, String>();
		for (String word : wordsList) {
			if (isCorrect(word, frontSpaces, blankSpaces, letter)) {
				// System.out.println(word);
				wordSet.put(getSorted(word), word);
			}
		}
		return wordSet;
	}

	private boolean isCorrect(String word, int frontSpaces, int backSpaces, char letter) {
		// return word.matches("[A-Z]{0," + frontSpaces + "}" + letter +
		// "[A-Z]{0," + backSpaces + "}");
		return word.matches(String.format("[A-Z]{0,%d}%c[A-Z]{0,%d}", frontSpaces, letter, backSpaces));

	}

	public Map<String, String> readFile(String path, int frontSpaces, int blankSpaces, char letter) throws Exception {
		return getWordMap((ArrayList<String>) Files.readAllLines(Paths.get(path)), frontSpaces, blankSpaces, letter);
	}

	public boolean isValid(String s, Map<String, String> dict, int numOfBlanks, char letter) {
		String regstr = s.replaceAll("", ".*");
		for (Map.Entry<String, String> entry : dict.entrySet()) {
			if (entry.getKey().matches(regstr) && (entry.getKey().length() - s.length() <= numOfBlanks)) {
				//System.out.println(entry.getKey() + " " + entry.getValue());
				wordMatch = entry.getValue();
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

	public int getMaxScore(String str, Map<String, String> dictionary, char letter) {
		int maxScore = 0;
		String maxWord = "";
		String sortedStrWithoutBlank = removeBlanksSorted(str);
		Set<String> permutations = Permutations.permutationFinder(sortedStrWithoutBlank);
		int numOfBlanks = str.length() - sortedStrWithoutBlank.length();

		for (String permWord : permutations) {
			if (isValid(getPermWord(permWord, letter), dictionary, numOfBlanks, letter) && maxScore < totalScore(permWord)) {
				maxScore = totalScore(permWord);
				maxWord = wordMatch;
			}
		}
		
		System.out.println(maxWord);
		return maxScore;
	}

	private String getPermWord(String permWord, char letter) {
	//	if(permWord.contains(String.valueOf(letter)))
		//	return permWord;
		return getSorted(permWord + letter);
	}

	private String removeBlanksSorted(String str) {
		return getSorted(str.replaceAll(BLANK, ""));
	}

	public static void main(String args[]) throws Exception {
		Scrabble sc = new Scrabble();
		String filePath = args[0];
		String input = args[1].toUpperCase();
		int frontSpaces  = Integer.parseInt(args[2]);
		char letter = args[3].charAt(0);
		int backSpaces = Integer.parseInt(args[4]); 
		Map<String, String> dictionary = sc.readFile(filePath, frontSpaces, backSpaces, letter);
		System.out.println(sc.getMaxScore(input, dictionary, 'K'));
		// System.out.println(dictionary);
	}
}