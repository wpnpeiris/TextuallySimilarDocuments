/**
 * 
 */
package com.wpnpeiris.datamining.similardocuments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author pradeeppeiris
 *
 */
public class Main {

	private static final int NUM_SHINGLES = 5;
	private static final int NUM_HASHFUNS = 100;

	private static final int NUM_BANDS = 20;
	private static final int NUM_ROWS = 5;
	private static final int NUM_BUCKETS = 61;

	private static final double THRESHOLD = 0.8;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		if(args.length == 0) {
			throw new Exception("Insufficent arguments");
		}
		
		String dataFolder = args[0];
		
		Map<String, String> documents = loadDocuments(dataFolder);

		long startTime = System.currentTimeMillis();

		Map<String, List<Integer>> minHashSignatures = loadMinHashSignatures(documents);
		Set<String> similarDocuments = findSimilarDocuments(minHashSignatures);

		double elapsTime = (System.currentTimeMillis() - startTime);
		System.out.println("Completed in " + elapsTime + " ms");
		printSimilarDocuments(similarDocuments);
	}

	private static void printSimilarDocuments(Set<String> similarDocuments) {
		System.out.println("Similar Documents are: ");
		for (String pair : similarDocuments) {
			String[] docsPair = pair.split(",");
			System.out.println(docsPair[0] + " and " + docsPair[1]);
		}
	}

	private static Set<String> findSimilarDocuments(Map<String, List<Integer>> minHashSignatures) {
		LocalitySensitiveHashing lsh = new LocalitySensitiveHashing(minHashSignatures, THRESHOLD, NUM_BANDS, NUM_ROWS,
				NUM_BUCKETS);
		return lsh.generateCandidatePairs();
	}

	private static Map<String, List<Integer>> loadMinHashSignatures(Map<String, String> documents) {
		List<Integer> coefficients = getCoefficients();
		List<Integer> constants = getConstants();

		Map<String, List<Integer>> minHashSignatures = new HashMap<>();
		for (String doc : documents.keySet()) {
			Set<Integer> k_shingles = new Shingles(documents.get(doc)).generateHashedShingles(NUM_SHINGLES);
			minHashSignatures.put(doc,
					new MinHashing(k_shingles, NUM_HASHFUNS, coefficients, constants).generateMinHashSingnatures());
		}
		return minHashSignatures;
	}

	private static Map<String, String> loadDocuments(String path) throws IOException {
		System.out.println("Read files in " + path);
		CharsetDecoder dec = StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.IGNORE);
		Map<String, String> documents = new HashMap<>();
		Files.list(Paths.get(path)).filter(Files::isRegularFile).forEach(file -> {
			StringBuilder fileContent = new StringBuilder();

			try (Reader r = Channels.newReader(FileChannel.open(file), dec, -1);
					BufferedReader br = new BufferedReader(r)) {
				br.lines().forEach(l -> fileContent.append(l).append("\n"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			documents.put(file.toString(), fileContent.toString());
		});
		return documents;
	}

	private static List<Integer> getCoefficients() {
		return IntStream.range(1, NUM_HASHFUNS + 1).boxed().collect(Collectors.toList());
	}

	private static List<Integer> getConstants() {
		Integer[] constants = new Integer[NUM_HASHFUNS];
		Arrays.fill(constants, 1);
		return Arrays.asList(constants);
	}
}
