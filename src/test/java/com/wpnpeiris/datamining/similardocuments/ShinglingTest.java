/**
 * 
 */
package com.wpnpeiris.datamining.similardocuments;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

/**
 * @author pradeeppeiris
 *
 */
public class ShinglingTest {

	Shingles shingling;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		String document = "This is a sample document for Shingling test, OK. Note! it is a sample document.";
		shingling = new Shingles(document);
	}

	@Test
	public void testCreateBagOfWords() {
		List<String> bagOfWords = Arrays.asList(shingling.createBagOfWords());
		bagOfWords.forEach(System.out::println);

		assertThat(bagOfWords,
				CoreMatchers.hasItems("This", "is", "a", "sample", "document", "for", "Shingling", "test", "OK"));
	}
	
	@Test
	public void testGenerate3Shingling() {
		Set<String> shinglings = shingling.generateShingles(3);
		shinglings.forEach(System.out::println);
		
		assertThat(shinglings,
				CoreMatchers.hasItems("This is a", 
						"is a sample", 
						"a sample document", 
						"document for Shingling", 
						"for Shingling test", 
						"Shingling test OK", 
						"test OK Note", 
						"OK Note it"));
	}

	@Test
	public void testGenerate4Shingling() {
		Set<String> shinglings = shingling.generateShingles(4);
		shinglings.forEach(System.out::println);
		
		assertThat(shinglings,
				CoreMatchers.hasItems("This is a sample", 
				"is a sample document", 
				"a sample document for", 
				"sample document for Shingling",
				"document for Shingling test", 
				"for Shingling test OK", 
				"Shingling test OK Note", 
				"test OK Note it", 
				"OK Note it is", 
				"Note it is a",
				"This is a sample"));
	}
	
	@Test
	public void testGenerate10Shingling() {
		Set<String> shinglings = shingling.generateShingles(10);
		shinglings.forEach(System.out::println);
		
		assertThat(shinglings,
				CoreMatchers.hasItems("This is a sample document for Shingling test OK Note", 
				"is a sample document for Shingling test OK Note it", 
				"a sample document for Shingling test OK Note it is", 
				"sample document for Shingling test OK Note it is a",
				"document for Shingling test OK Note it is a sample", 
				"for Shingling test OK Note it is a sample document"));
	}
	
	@Test
	public void testGenerate10HasedShingling() {
		Set<Integer> shinglings = shingling.generateHashedShingles(10);
		shinglings.forEach(System.out::println);
		
		assertThat(shinglings,
				CoreMatchers.hasItems("This is a sample document for Shingling test OK Note".hashCode(), 
				"is a sample document for Shingling test OK Note it".hashCode(), 
				"a sample document for Shingling test OK Note it is".hashCode(), 
				"sample document for Shingling test OK Note it is a".hashCode(),
				"document for Shingling test OK Note it is a sample".hashCode(), 
				"for Shingling test OK Note it is a sample document".hashCode()));
	}
}
