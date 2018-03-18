/**
 * 
 */
package com.wpnpeiris.datamining.similardocuments;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author pradeeppeiris
 *
 */
public class Shingles {
	private static final String NON_ALPHANUMERIC_CHARACTER = "\\W+";
	private final String document;

	public String getDocument() {
		return document;
	}

	public Shingles(String document) {
		this.document = document;
	}
	
	/**
	 * Generate k-Shingles for the document
	 * 
	 * @param k 
	 * 		Shingle size
	 * @return
	 * 		A set of k-Shingles of the document
	 */
	public Set<String> generateShingles(int k) {
		String[] bagOfWords = createBagOfWords();
		
		return createShingles(k, bagOfWords);
	}
	
	/**
	 * Generate Hashed k-Shingles for the document
	 * 
	 * @param k 
	 * 		Shingle size
	 * @return
	 * 		A set of Hashed k-Shingles of the document
	 */
	public Set<Integer> generateHashedShingles(int k) {
		System.out.println("Generate hashed shingles of size " + k);
		String[] bagOfWords = createBagOfWords();
		Set<String> shingles = createShingles(k, bagOfWords);
		Set<Integer> hashedShingles = shingles.stream().map(s -> s.hashCode()).collect(Collectors.toSet());
		
		return sortHashedShingles(hashedShingles);
	}

	protected Set<Integer> sortHashedShingles(Set<Integer> hashedShingles) {
		Set<Integer> sortedHashedShingles = new TreeSet<>();
		sortedHashedShingles.addAll(hashedShingles);
		return sortedHashedShingles;
	}

	protected Set<String> createShingles(int k, String[] bagOfWords) {
		Set<String> shingles = new HashSet<>();
		for(int i = 0; i <= bagOfWords.length - k; i++) {
			StringBuilder shinglesPhrase = new StringBuilder();
			for(int j = i; j < (i + k); j++) {
				shinglesPhrase.append(bagOfWords[j]);
				if(j + 1 < i + k)
					shinglesPhrase.append(" ");
			}
			shingles.add(shinglesPhrase.toString());
		}
		
		return shingles;
	}

	protected String[] createBagOfWords() {
		return document.split(NON_ALPHANUMERIC_CHARACTER);
	}
}
