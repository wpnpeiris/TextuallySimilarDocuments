/**
 * 
 */
package com.wpnpeiris.datamining.similardocuments;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author pradeeppeiris
 *
 */
public class JaccardSimilarity {

	public static double compute(Set<Integer> set1, Set<Integer> set2) {
		Set<Integer> intersection = getIntersection(set1, set2);
		Set<Integer> union = getUnion(set1, set2);
		
		return intersection.size() / Double.valueOf(union.size());
	}
	
	public static double compute(List<Integer> list1, List<Integer> list2) {
		return compute(new HashSet<>(list1), new HashSet<>(list2));
	}

	protected static Set<Integer> getIntersection(Set<Integer> set1, Set<Integer> set2) {
		Set<Integer> intersection = new HashSet<Integer>(set1);
		intersection.retainAll(set2);
		
		return intersection;
	}
	
	protected static Set<Integer> getUnion(Set<Integer> set1, Set<Integer> set2) {
		Set<Integer> union = new HashSet<Integer>(set1);
		union.addAll(set2);
		
		return union;
	}
}
