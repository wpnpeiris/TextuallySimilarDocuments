# Similar Documents
Finding similar document is the problem of finding documents containing common text. The main challenge is with a large document set that causes too many pairs to compare.

There are three essential techniques for finding similar document problem.

  - Shingling
  - Minhashing
  - Locality-sensitive hashing

### Shingling
Shingling converts documents into sets. The set can be a set of words appearing in a document, or it can be a set of important words. But it may not work properly as the ordering of words is not considered. k-shingles minimize the ordering problem.
K-Shingles for a document is a sequence of k token appears in the document.

The class Shingles provides the abstraction in gen- erating k number of shingles for given textual document.

```java
Shingles shingles = new Shingles (document ); 
Set<String> shinglings = shingles .generateShingles (4);
```
`Shingles.generateShingles(k:Integer)` returns k-shingles, ie substring of length k. 

`Shingles.generateHashedShingles(k:Integer)` computes a hash value for each shingles, and returns an ordered set of hashed k-shingles.

```java
Set<Integer> shinglings = shingles.generateHashedShingles (4);
```

#### Jaccard Similarity of Sets
The Jaccard similarity of sets S and T is `|S ∩ T|/|S ∪ T|`.

`JaccardSimilarity` class provides an implementation for Jaccard Similarity of given sets.

```java
JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
double similarity = jaccardSimilarity.compute(set1, set2);
```

### MinHash Signatures

A set of shingles for a document are usually large. For a million of documents, it may not possible to calculate and store all shingles-sets. Minhashing provides a way to replace a larger set of shingles with smaller representation called `signatures`.

`MinHashing` provides the abstraction for generating Minhashing signatures for a given set of hashed shingles.

```java
MinHashing minHashing = new (hashedShingles, numHashFunctions, coefficients, constants);
List<Integer> signatures = minHashing.generateMinHashSingnatures();
```

The key idea of MinHash signature is;
  - It take *k* independent hash functions
  - Apply hash function to the elements, which result a vector of k minHash values
  
### Locality-Sensitive Hashing (LSH)

Minihashing compresses a large set of shingles into small signatures. But, the number of pairs in a document set can be still large for the computation. Locality-sensitive hashing
(LSH) provides a way to identify document pairs that are likely to be similar, without investigating every pair. 
`LocalitySensitiveHashing` provides the implementation for finding possible similar pairs for a given set of minhasing signatures.

```java
LocalitySensitiveHashing lsh = new LocalitySensitiveHashing(minHashSignatures, threshold, bands, rows, bucketSize);
Set<String> candidatePairs = lsh.generateCandidatePairs();
```