'''Encrypt the message "I am learning #information security" using each of the #following
ciphers. Ignore the space between words. #Decrypt the message to get the original
plaintext:
a) Additive cipher with key = 20
b) Multiplicative cipher with key = 15
c) Affine cipher with key = (15, 20)

#Code :- 
'''
def mod_inverse(a, m):
    # Find modular multiplicative inverse of 'a' under modulo 'm'
    for i in range(m):
        if (a*i) % m == 1:
            return i
    return None

def encrypt(message, key, type):
    # Remove spaces and convert message to lowercase
    message = message.replace(" ", "").lower()
    encrypted_message = []

    for char in message:
        if char.isalpha():   # Process only alphabetic characters
            num = ord(char) - ord('a')  # Convert character to number (0-25)

            # Apply chosen cipher type
            if type == 'add':  
                num = (num + key) % 26   # Additive cipher
            elif type == 'mul':
                num = (num * key) % 26   # Multiplicative cipher

            encrypted_char = chr(num + ord('a'))  # Convert back to character
            encrypted_message.append(encrypted_char)
        else:
            # Keep non-alphabetic characters unchanged
            encrypted_message.append(char)

    return ''.join(encrypted_message)

def decrypt(encrypted_message, key, type):
    decrypted_message = []

    for char in encrypted_message:
        if char.isalpha():   # Process only alphabetic characters
            num = ord(char) - ord('a')
            if type == 'add':
                num = (num - key) % 26   # Reverse additive cipher
            elif type == 'mul':
                # Find modular inverse for multiplicative decryption
                inverse_key = mod_inverse(key, 26)
                num = (num * inverse_key) % 26
            decrypted_char = chr(num + ord('a'))
            decrypted_message.append(decrypted_char)
        else:
            decrypted_message.append(char)

    return ''.join(decrypted_message)

def affencryption(message, k1, k2):
    # Affine cipher encryption: (k1*x + k2) mod 26
    message = message.replace(" ", "").lower()
    encmsg = ""
    for c in message:
        num = ord(c) - ord('a')
        num = (k1 * num + k2) % 26
        numch = chr(num + ord('a'))
        encmsg += numch
    return encmsg

def affdec(message, k1, k2):
    # Affine cipher decryption: a^-1(x - b) mod 26
    message = message.replace(" ", "").lower()
    encmsg = ""
    n = mod_inverse(k1, 26)  # Find modular inverse of k1
    for c in message:
        num = ord(c) - ord('a')
        num = (n * (num - k2)) % 26
        numch = chr(num + ord('a'))
        encmsg += numch
    return encmsg

# Test message
message = "I am learning information security"
key = 20

# Affine cipher encryption
encrypted_message = affencryption(message, 15, 20)
print("Encrypted Message:", encrypted_message)

# Affine cipher decryption
decrypted_message = affdec(encrypted_message, 15, 20)
print("Decrypted Message:", decrypted_message)

# Multiplicative cipher encryption
print("Multiplicative Encryption:", encrypt(message, 15, 'mul'))

        
