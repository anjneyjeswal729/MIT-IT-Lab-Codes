'''Implement the hash function in Python. Your function should start with an initial hash
 value of 5381 and for each character in the input string, multiply the current hash value
 by 33, add the ASCII value of the character, and use bitwise operations to ensure
 thorough mixing of the bits. Finally, ensure the hash value is kept within a 32-bit range
 by applying an appropriate mask.'''


def hashing(s):
    hashval = 5381                        # Initialize hash value (common starting point in DJB2)
    for char in s:
        hashval = (hashval * 33) + ord(char)  # Multiply by 33 and add ASCII value of character

    hashval = hashval & 0xFFFFFFFF        # Limit to 32-bit unsigned integer
    return hashval

msg = "Ayush"
print(hashing(msg))                       # Print the hash of the message
