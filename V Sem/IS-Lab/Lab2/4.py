'''Encrypt the message "Classified Text" using Triple DES with the key
 "1234567890ABCDEF1234567890ABCDEF1234567890ABCDEF". 
Then
 decrypt the ciphertext to verify the original message.'''

from Crypto.Cipher import DES3
from Crypto.Util.Padding import pad, unpad
from Crypto.Random import get_random_bytes
import base64

# Define a 24-byte (192-bit) key using hex string
key = bytes.fromhex('1234567890abcdef0123456789abcdef1234567890abcdef')

# Just printing length of a random hex string (not used in encryption/decryption)
print(len('401b7cfe0ee5b7f4bca2275834b2bc146b8bc7f7c8d7d671'))

# Print raw key (bytes)
print(key)

# Message to encrypt
text1 = b'Classified Text'

# Print key in hexadecimal format
print(key.hex())

# Pad the message to match block size (8 bytes for DES3)
padded_text = pad(text1, DES3.block_size)

# Create DES3 cipher in CBC mode (random IV generated internally)
des = DES3.new(key, DES3.MODE_CBC)

# Encrypt the padded text
encrypted_text = des.encrypt(padded_text)

# Encode ciphertext in base64 for safe display
enc_text = base64.b64encode(encrypted_text).decode('ascii')
print("Encrypted text: ", enc_text)

# Create DES3 cipher again with the same key and IV for decryption
des_decrypt = DES3.new(key, DES3.MODE_CBC, iv=des.iv)

# Decrypt the ciphertext
decrypted_padded = des_decrypt.decrypt(encrypted_text)

# Remove padding to get original plaintext
decrypted_text = unpad(decrypted_padded, DES3.block_size)

# Print decrypted text
print("Decrypted text:", decrypted_text.decode())
