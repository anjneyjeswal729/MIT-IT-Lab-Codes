 '''Encrypt the message "Confidential Data" using DES with the following key:
 "A1B2C3D4". Then decrypt the ciphertext to verify the original message.'''

from Crypto.Cipher import DES                # DES cipher implementation
from Crypto.Util.Padding import pad, unpad   # Helpers to pad/unpad to DES block size
import base64                                # For readable ciphertext output

key = b'A1B2C3D4'                            # DES key must be 8 bytes
text1 = b'Confidential Data!'                # Plaintext as bytes

padded_text = pad(text1, DES.block_size)     # Pad plaintext to a multiple of 8 bytes

des = DES.new(key, DES.MODE_ECB)             # Create DES cipher object in ECB mode

encrypted_text = des.encrypt(padded_text)    # Encrypt the padded plaintext
enc_text = base64.b64encode(encrypted_text).decode('ascii')  # Base64 for printable output
print("Encrypted text: ", enc_text)          # Show ciphertext

des_decrypt = DES.new(key, DES.MODE_ECB)     # Create a new DES object for decryption
decrypted_padded = des_decrypt.decrypt(encrypted_text)  # Decrypt back to padded plaintext

decrypted_text = unpad(decrypted_padded, DES.block_size)  # Remove padding to recover original message

print("Decrypted text:", decrypted_text.decode())  # Print decrypted plaintext (decoded to str)

