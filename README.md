# Data Compression

The nucleotides that make up the genes in DNA molecules are designated by one of four values: A, C, G, or T. When the description of the gene is stored in a variable-type string, two bytes, or 16 bits, are required to encode each nucleotide because symbol-type strings in Java consist of Unicode symbols. But two bits can be used to encode the four possible values, e.g., a with bits 00, C with bits 01, G with 10, and T with 11. Then the memory required to describe the gene can be reduced eightfold (from 16 bits to 2 bits for encoding each nucleotide).

## Functions:
### Data compression

```
comp (String line) -> byte[] encoded

>>> comp CGATAAG
7 63 8
```
Compression of symbol-like strings into a byte array, where each letter is encoded in two bits <br/>
* Input - string of [A, C, G, T] letters (case-insensitive) <br/>
* Output - encoded in hex byte array (1st element in the array - number of letters)

### Data decompression
```
decomp (n k b1 b2 b3 ... bn) -> byte[] hex, String decoded

>>> decomp 4 10 30 0127 32
A 1E 81 20
ACTGGAACAG
```
Decompress the byte array into a symbol-like string [A, C, G, T], decoding every two bits into one letter <br/>
* Input - decimal byte array (1st element in the array - byte count, 2nd element - letter count) <br/>
* Output - hexadecimal byte array (1st element in the array - number of letters) and decoded string of [A, C, G, T] letters



  
