/**
 * 
 */
package com.wpnpeiris.datamining.similardocuments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author pradeeppeiris
 *
 */
public class LocalitySensitiveHashingTest {
	LocalitySensitiveHashing lsh;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Set<Integer> doc1 = new Shingles("aaa bbb ccc ddd eee").generateHashedShingles(4);
		Set<Integer> doc2 = new Shingles("fff ggg hhh iii jjj kkk").generateHashedShingles(4);
		Set<Integer> doc3 = new Shingles("ggg hhh iii jjj kkk lll").generateHashedShingles(4);
		Set<Integer> doc4 = new Shingles("mmm nnn ooo ppp qqq rrr sss").generateHashedShingles(4);
		Set<Integer> doc5 = new Shingles("ttt uuu vvv www xxx yyy zzz").generateHashedShingles(4);
		
		List<Integer> coefficients = Arrays.asList(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
		List<Integer>  constants = Arrays.asList(new Integer[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
		
		Map<String, List<Integer>> minHashSignatures = new HashMap<>();
		minHashSignatures.put("doc1", new MinHashing(doc1, 12, coefficients, constants).generateMinHashSingnatures());
		minHashSignatures.put("doc2", new MinHashing(doc2, 12, coefficients, constants).generateMinHashSingnatures());
		minHashSignatures.put("doc3", new MinHashing(doc3, 12, coefficients, constants).generateMinHashSingnatures());
		minHashSignatures.put("doc4", new MinHashing(doc4, 12, coefficients, constants).generateMinHashSingnatures());
		minHashSignatures.put("doc5", new MinHashing(doc5, 12, coefficients, constants).generateMinHashSingnatures());
		
		lsh = new LocalitySensitiveHashing(minHashSignatures, 0.2, 4, 3, 7);
	}

	@Test
	public void testCreatePossiblePairs() {
		lsh.createPossiblePairs();
	}
	
	@Test
	public void testGenerateCandidatePairs() {
		Set<String> candidatePairs = lsh.generateCandidatePairs();
		assertEquals(candidatePairs.size(), 1);
	}

}
