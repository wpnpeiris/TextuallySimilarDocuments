/**
 * 
 */
package com.wpnpeiris.datamining.similardocuments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author pradeeppeiris
 *
 */
public class LocalitySensitiveHashing {
	private final Map<String, List<Integer>> minHashSignatures;
	private final double threshold;
	private final int bandsCount;
	private final int rowCount;
	private final int bucketSize;
	
	public LocalitySensitiveHashing(Map<String, List<Integer>> minHashSignatures, double threshold, int bandsCount, int rowCount, int bucketSize) {
		this.minHashSignatures = minHashSignatures;
		this.threshold = threshold;
		this.bandsCount = bandsCount;
		this.rowCount = rowCount;
		this.bucketSize = bucketSize;
	}

	public Set<String> generateCandidatePairs() {
		Set<String> candidatePairs = new HashSet<>();
		Set<String> possiblePairs = createPossiblePairs();
		for(String pairsKey : possiblePairs) {
			List<Integer> minHashSignatures1 = minHashSignatures.get(pairsKey.split(",")[0]);
			List<Integer> minHashSignatures2 = minHashSignatures.get(pairsKey.split(",")[1]);
			double jaccardSimilarity = JaccardSimilarity.compute(minHashSignatures1, minHashSignatures2);
			System.out.println("JaccardSimilarity for Sets: " + pairsKey + " is " + jaccardSimilarity);
			
			if(jaccardSimilarity >= threshold ) {
				System.out.println("Found candidate pair: " + pairsKey + " with JaccardSimilarity: " + jaccardSimilarity);
				candidatePairs.add(pairsKey);
			}
		}
		
		return candidatePairs;
	}
	
	protected Set<String> createPossiblePairs() {
		Map<Integer, Set<String>> possibleSimilarSets = new HashMap<>();
		for(String doc : minHashSignatures.keySet()) {
			List<Integer> docMinHashSignature = minHashSignatures.get(doc);
			List<List<Integer>> bandMinHasSignatures = createBandOfMinSignatures(docMinHashSignature);
			System.out.println(doc + ": band minhash singnatures " + bandMinHasSignatures);
			
			List<Integer> hashedBands = createHashForBands(bandMinHasSignatures);
			System.out.println(doc + ": hashed bands " + hashedBands);
			
			updateSimilarSets(possibleSimilarSets, doc, hashedBands);
		}
		
		System.out.println("Possible Similar Sets " + possibleSimilarSets);
		
		Set<String> possiblePairs = createSimilarPairs(possibleSimilarSets);	
		System.out.println("Possible Similar Pairs " + possiblePairs);
		
		return possiblePairs;
	}

	private Set<String> createSimilarPairs(Map<Integer, Set<String>> similarSets) {
		Set<String> similarPairs = new HashSet<>();
		for(Set<String> similarSet : similarSets.values()) {
			if(similarSet.size() > 1) {
				String[] similarSetArray = similarSet.toArray(new String[similarSet.size()]);
				for(int i = 0; i < similarSetArray.length - 1; i++) {
					similarPairs.add(similarSetArray[i] + "," + similarSetArray[i + 1]);
				}
			}
		}
		return similarPairs;
	}

	private void updateSimilarSets(Map<Integer, Set<String>> similarSets, String doc, List<Integer> hashedBands) {
		for(Integer hashKey : hashedBands) {
			if(similarSets.containsKey(hashKey)) {
				similarSets.get(hashKey).add(doc);
			} else {
				similarSets.put(hashKey, new TreeSet<>(Arrays.asList(doc)));
			}
		}
	}

	private List<List<Integer>> createBandOfMinSignatures(List<Integer> docMinHashSignature) {
		List<List<Integer>> bandMinHasSignatures = new ArrayList<>();
		for(int b = 1; b <= bandsCount; b++){
			bandMinHasSignatures.add(docMinHashSignature.subList((b * rowCount) - rowCount, (b * rowCount)));
		}
		
		return bandMinHasSignatures;
	}
	
	private List<Integer> createHashForBands(List<List<Integer>> bandMinHashSignatures) {
		List<Integer> hashedBands = new ArrayList<>();
		for(List<Integer> bandMinHashSignature : bandMinHashSignatures) {
			hashedBands.add(Math.abs(Arrays.hashCode(bandMinHashSignature.toArray(new Integer[bandMinHashSignatures.size()])) % bucketSize));
		}
		
		return hashedBands;
	}
}
	