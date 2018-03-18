/**
 * 
 */
package com.wpnpeiris.datamining.similardocuments;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author pradeeppeiris
 *
 */
public class MinHashing {
	private final int MAX_PRIME = 65563;
	private final Set<Integer> hashedShingles;
	private final int k;
	private final List<Integer> coefficients;
	private final List<Integer> constants;
	
	public MinHashing(Set<Integer> hashedShingles, int k, List<Integer> coefficients, List<Integer> constants) {
		this.hashedShingles = hashedShingles;
		this.k = k;
		this.coefficients = coefficients;
		this.constants = constants;
	}
	
	public List<Integer> generateMinHashSingnatures() {
		Integer[] minHashSingnatures = initiateMinHashSingnatures();
		
		int hashSignature;
		for(int i = 0; i < k; i++) {	
			for(int shingles : hashedShingles) {
				hashSignature = createHashSingnature(coefficients.get(i), constants.get(i), shingles);
				if(hashSignature < minHashSingnatures[i]) {
					minHashSingnatures[i] = hashSignature;
				}
			}
		}
		
		return Arrays.asList(minHashSingnatures);
	}

	private Integer[] initiateMinHashSingnatures() {
		Integer[] minHashSingnatures = new Integer[k];
		Arrays.fill(minHashSingnatures, Integer.MAX_VALUE);
		return minHashSingnatures;
	}
	
	private int createHashSingnature(int coefficient, int constant, int shingles) {
		return (coefficient * shingles + constant) % MAX_PRIME;
	}
}	
