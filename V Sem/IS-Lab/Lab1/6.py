'''Use a brute-force attack to decipher the following message. Assume that you know it is
 an affine cipher and that the plaintext "ab" is enciphered to "GL":
 XPALASXYFGFUKPXUSOGEUTKCDGEXANMGNVS'''

def mod_inverse(a, m):
    # Find modular multiplicative inverse of 'a' under modulo 'm'
    a = a % m
    for x in range(1, m):
        if (a * x) % m == 1:
            return x
    return None

def affine_decrypt(ciphertext, a, b, m=26):
    # Decrypt ciphertext using affine cipher formula: x = a_inv * (y - b) mod m
    a_inv = mod_inverse(a, m)
    if a_inv is None:
        return "No modular inverse found for a =", a
    
    plaintext = []
    
    for char in ciphertext:
        if char.isalpha():  # Process only letters
            y = ord(char.upper()) - ord('A')     # Convert char to number (0-25)
            x = (a_inv * (y - b)) % m            # Apply affine decryption formula
            decrypted_char = chr(x + ord('A'))   # Convert back to letter
            plaintext.append(decrypted_char)
        else:
            plaintext.append(char)  # Keep non-letters unchanged
    
    return ''.join(plaintext)

def find_affine_parameters(plaintext_pair, ciphertext_pair, m=26):
    # Find 'a' and 'b' of affine cipher using two known plaintext–ciphertext pairs
    (p1, p2) = plaintext_pair
    (c1, c2) = ciphertext_pair
    
    # Convert characters to numbers (0-25)
    p1_num, p2_num = ord(p1) - ord('A'), ord(p2) - ord('A')
    c1_num, c2_num = ord(c1) - ord('A'), ord(c2) - ord('A')
    
    # Try possible values of 'a'
    for a in range(1, m):
        if mod_inverse(a, m) is None:  # Skip if 'a' has no inverse
            continue
        
        # Compute possible 'b' using first pair
        b = (c1_num - a * p1_num) % m
        
        # Check if it also satisfies second pair
        if (a * p2_num + b) % m == c2_num:
            return a, b   # Found valid (a, b)
    
    return None  # No valid parameters found

# --- Main Program ---

# Known plaintext and ciphertext pairs
plaintext_pair = ("A", "B")       # Example: 'A' -> 'G', 'B' -> 'L'
ciphertext_pair = ("G", "L")

# Find affine cipher parameters (a, b)
parameters = find_affine_parameters(plaintext_pair, ciphertext_pair)

if parameters:
    a, b = parameters
    print(f"Affine cipher parameters found: a = {a}, b = {b}")

    # Ciphertext to decrypt
    ciphertext = "XPALASXYFGFUKPXUSOGEUTKCDGEXANMGNVS"
    
    # Decrypt using found parameters
    plaintext = affine_decrypt(ciphertext, a, b)
    print(f"Decrypted message: {plaintext}")
else:
    print("Affine cipher parameters not found.")

