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