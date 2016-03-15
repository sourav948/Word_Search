package com.zafin.interview;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author sourav
 *
 */
public class Result {

	private static char[][] twoDimArray = null;
	private static Set<String> validWords = new HashSet<>();
	private static Set<String> wordsFound = new HashSet<>();

	public static void main(String[] args) throws IOException {
		StringBuilder wordsBlock = readInput();
		Result solution = new Result();
		solution.loadValidWords("dictionary.txt");
		solution.convertToTwoDArray(wordsBlock);
		solution.extractWords();
		Set<String> result = solution.convertToUpperCase(wordsFound);
		System.out.println(result);
	}
	
	/**
	 * convert the result to uppercase 
	 * @param wordsFound2
	 * @return
	 */
	private Set<String> convertToUpperCase(Set<String> wordsFound2) {
		Set<String> result = new HashSet<>();
		for (String string : wordsFound2) {
			result.add(string.toUpperCase());
		}
		return result;
	}

	/**
	 * Read the input file and build the string builder
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static StringBuilder readInput() throws IOException, FileNotFoundException {
		StringBuilder wordsBlock = new StringBuilder();

		String line;
		try( BufferedReader br = new BufferedReader(new FileReader("input.txt"))){
			while ((line = br.readLine()) != null && line.length()!= 0) {
				wordsBlock.append(line).append("\n");
			}
		}
		return wordsBlock;
	}

	/**
	* 	 Import valid words from dictionary file.
	*/
	private void loadValidWords(String validWordsLocation) throws FileNotFoundException, IOException {
		String line;
		try(BufferedReader br = new BufferedReader(new FileReader(validWordsLocation))){
			while ((line = br.readLine()) != null) {
				validWords.add(line);
			}
		}
	}

	/**
	 * Converts input character block to two dimensional array
	 * 
	 * @param inputBlocks
	 */
	private void convertToTwoDArray(StringBuilder inputBlocks) {
		String[] lines = inputBlocks.toString().split("\n");
		twoDimArray = new char[lines.length][lines[0].length()];

		for(int i = 0; i < lines.length; i++)  {
			String line = lines[i];
			twoDimArray[i] = line.toLowerCase().toCharArray();
		}
	}

	/**
	 * Traverse twoDimArray to search valid words
	 * 
	 */
	private void extractWords() {
		for (int i = 0; i < twoDimArray.length; i++) {
			for (int j = 0; j < twoDimArray[0].length; j++) {
				findValidWords(twoDimArray[i][j], i, j);
			}
		}
	}

	/**
	 * Traverse the input block in all the directions 
	 * 
	 * @param ch - First character of the word.
	 * @param i -  location of a character at row
	 * @param j - location of a character at column 
	 */
	private static void findValidWords(char ch, int i, int j) {
		//traverse left
		StringBuilder word = new StringBuilder(String.valueOf(ch));
		for (int k = j - 1; k >= 0; k--) {
			if (validWords.contains(word.append(twoDimArray[i][k]).toString())) {
				wordsFound.add(word.toString());
			}
		}

		//traverse right
		word = new StringBuilder(String.valueOf(ch));
		for (int k = j + 1; k < twoDimArray[i].length; k++) {
			if (validWords.contains(word.append(twoDimArray[i][k]).toString())) {
				wordsFound.add(word.toString());
			}
		}

		//traverse bottom
				word = new StringBuilder(String.valueOf(ch));
				for (int k = i + 1; k < twoDimArray.length; k++) {
					if (validWords.contains(word.append(twoDimArray[k][j]).toString())) {
						wordsFound.add(word.toString());
					}
				}
				
		//traverse top
		word = new StringBuilder(String.valueOf(ch));
		for (int k = i - 1; k >= 0; k--) {
			if (validWords.contains(word.append(twoDimArray[k][j]).toString())) {
				wordsFound.add(word.toString());
			}
		}

		//traverse left diagonal
		word = new StringBuilder(String.valueOf(ch));
		int k = i-1;
		int l = j-1;
		while (k >= 0 && l >= 0) {
			if (validWords.contains(word.append(twoDimArray[k--][l--]).toString())) {
				wordsFound.add(word.toString());
			}
		}

		//traverse right diagonal
		word = new StringBuilder(String.valueOf(ch));
		k = i-1;
		l = j+1;
		while (k >= 0 && l < twoDimArray[i].length) {
			if (validWords.contains(word.append(twoDimArray[k--][l++]).toString())) {
				wordsFound.add(word.toString());
			}
		}

		//traverse reverse left diagonal
		word = new StringBuilder(String.valueOf(ch));
		k = i+1;
		l = j-1;
		while (k < twoDimArray.length && l >= 0) {
			if (validWords.contains(word.append(twoDimArray[k++][l--]).toString())) {
				wordsFound.add(word.toString());
			}
		}

		//traverse reverse right diagonal
		word = new StringBuilder(String.valueOf(ch));
		k = i+1;
		l = j+1;
		while (k < twoDimArray.length && l < twoDimArray[i].length) {
			if (validWords.contains(word.append(twoDimArray[k++][l++]).toString())) {
				wordsFound.add(word.toString());
			}
		}
	}
}