package com.wpnpeiris.datamining.similardocuments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

public class JaccardSimilarityTest {
	JaccardSimilarity jaccardSimilarity;
	
	@Before
	public void setUp() throws Exception {
		jaccardSimilarity = new JaccardSimilarity();
	}

	@Test
	public void testGetIntersection() {
		Set<Integer> set1 = new HashSet<>(Arrays.asList(new Integer[]{2, 3, 4, 5}));
		Set<Integer> set2 = new HashSet<>(Arrays.asList(new Integer[]{4, 5, 6, 7}));
		Set<Integer> intersection = jaccardSimilarity.getIntersection(set1, set2);
		intersection.forEach(System.out::println);
		
		assertThat(intersection, CoreMatchers.hasItems(4, 5));
	}

	@Test
	public void testGetIUnionSet() {
		Set<Integer> set1 = new HashSet<>(Arrays.asList(new Integer[]{2, 3, 4, 5}));
		Set<Integer> set2 = new HashSet<>(Arrays.asList(new Integer[]{4, 5, 6, 7}));
		Set<Integer> union = jaccardSimilarity.getUnion(set1, set2);
		union.forEach(System.out::println);
		
		assertThat(union, CoreMatchers.hasItems(2, 3, 4, 5, 6, 7));
	}
	
	@Test
	public void testCompute() {
		Set<Integer> set1 = new HashSet<>(Arrays.asList(new Integer[]{2, 3, 4, 5}));
		Set<Integer> set2 = new HashSet<>(Arrays.asList(new Integer[]{4, 5, 6, 7}));
		
		double similarity = jaccardSimilarity.compute(set1, set2);
		System.out.println(similarity);
		assertEquals(similarity, 2 / 6d, 0.01);
	}
	
	@Test
	public void testComputeMinHashSignatures() {
		Shingles shingling1 = new Shingles("This is a sample document for Shingling test, OK. Note! it is a sample document.");
		Shingles shingling2 = new Shingles("The sample document explains about Shingling.");
		
		Set<Integer> hashSingles1 = shingling1.generateHashedShingles(10);
		Set<Integer> hashSingles2 = shingling2.generateHashedShingles(10);
		
		List<Integer> coefficients = Arrays.asList(new Integer[] {1, 2, 3, 4, 5});
		List<Integer>  constants = Arrays.asList(new Integer[] {1, 1, 1, 1, 1});
		
		List<Integer> hashSingnatures1 = new MinHashing(hashSingles1, 5, coefficients, constants).generateMinHashSingnatures();
		List<Integer> hashSingnatures2 = new MinHashing(hashSingles2, 5, coefficients, constants).generateMinHashSingnatures();
		
		
		double similarity = jaccardSimilarity.compute(hashSingnatures1, hashSingnatures2);
		System.out.println(similarity);
	}
	
}
