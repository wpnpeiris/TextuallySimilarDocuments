/**
 * 
 */
package com.wpnpeiris.datamining.similardocuments;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author pradeeppeiris
 *
 */
public class MinHashingTest {
	
	MinHashing minHashing;
	
	/**
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Shingles shingling = new Shingles("This is a sample document for Shingling test, OK. Note! it is a sample document.");
		Set<Integer> shinglings = shingling.generateHashedShingles(10);
		minHashing = new MinHashing(shinglings, 5, Arrays.asList(new Integer[] {1, 2, 3, 4, 5}), Arrays.asList(new Integer[] {1, 1, 1, 1, 1}));
	}

	@Test
	public void testGenerateMinHashSingnatures() {
		List<Integer> minHashSingnatures = minHashing.generateMinHashSingnatures();
		minHashSingnatures.forEach(System.out::println);
	}

}
