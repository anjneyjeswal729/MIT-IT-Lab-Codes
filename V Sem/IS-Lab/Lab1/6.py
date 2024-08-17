def mod_inverse(a, m):
    """Find the modular inverse of a under modulo m."""
    a = a % m
    for x in range(1, m):
        if (a * x) % m == 1:
            return x
    raise ValueError(f"No modular inverse for a={a} under modulo m={m}")

def affine_decrypt(ciphertext, a, b):
    """Decrypt an affine cipher text given the parameters a and b."""
    # The modular inverse of a modulo 26
    a_inv = mod_inverse(a, 26)
    
    plaintext = ""
    for char in ciphertext:
        if char.isalpha():  # Check if the character is a letter
            # Convert letter to number (A=0, B=1, ..., Z=25)
            y = ord(char.upper()) - ord('A')
            # Decrypt using the affine cipher decryption formula
            x = (a_inv * (y - b)) % 26
            # Convert number back to letter
            plaintext += chr(x + ord('A'))
        else:
            # Non-letter characters are not encrypted
            plaintext += char
    
    return plaintext

# Parameters of the affine cipher
a = 5
b = 6

# Ciphertext to decrypt
ciphertext = "XPALASXYFGFUKPXUSOGEUTKCDGEXANMGNVS"

# Decrypt the ciphertext
plaintext = affine_decrypt(ciphertext, a, b)

print(f"Decrypted message: {plaintext}")
