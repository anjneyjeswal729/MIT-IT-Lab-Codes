'''Encrypt the message "Top Secret Data" using AES-192 with the key
 "FEDCBA9876543210FEDCBA9876543210". Show all the steps involved in the
 encryption process (key expansion, initial round, main rounds, final round).'''

from Crypto.Cipher import AES
from Crypto.Util.Padding import pad, unpad
import base64

# 16-byte (128-bit) AES key defined in hex
key = bytes.fromhex("FEDCBA9876543210FEDCBA9876543210")

# Message to encrypt
message = "Top Secret Data"
data = message.encode()  # Convert string to bytes

# Create AES cipher in CBC mode (random IV generated automatically)
cipher = AES.new(key, AES.MODE_CBC)

# Encrypt the padded message
ctext = cipher.encrypt(pad(data, AES.block_size))

# Create new cipher object for decryption with same key and IV
cipher_decrypt = AES.new(key, AES.MODE_CBC, iv=cipher.iv)

# Decrypt and unpad to retrieve original plaintext
plaintext = unpad(cipher_decrypt.decrypt(ctext), AES.block_size)

# Print encrypted message in Base64 (for readability)
print("Encrypted message: ", base64.b64encode(ctext).decode('ascii'))

# Print decrypted plaintext
print("Decrypted message: ", plaintext.decode())
