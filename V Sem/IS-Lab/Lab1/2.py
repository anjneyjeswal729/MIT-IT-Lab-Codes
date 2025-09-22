'''Encrypt the message "the house is being sold tonight" using each of the following
 ciphers. Ignore the space between words. Decrypt the message to get the original
 plaintext:
 a) Vigenere cipher with key: "dollars"
 b) Autokey cipher with key = 7
 '''
def keymsg(jk, leng):
    # Extend/repeat the given key to match the length of the message
    key = jk
    while len(key) < leng:
        key += jk
    key = key[:leng]   # Trim if it's longer than needed
    return key 

def encrypt(message, key, autokey):
    # Vigenère and Autokey encryption
    message = message.lower()
    li = []
    encmsg = ""

    # --- Vigenère Encryption ---
    for i in range(len(message)):
        num = ord(message[i]) - ord('a')      # Convert char to number (0-25)
        num1 = ord(key[i]) - ord('a')         # Corresponding key char
        numch = chr(((num + num1) % 26) + ord('a'))
        encmsg += numch
    li.append(encmsg)   # Save Vigenère ciphertext

    # --- Autokey Encryption ---
    encmsg = ""
    for i in range(len(message)):
        num = ord(message[i]) - ord('a')
        num1 = ord(autokey[i]) - ord('a')
        numch = chr(((num + num1) % 26) + ord('a'))
        encmsg += numch
    li.append(encmsg)   # Save Autokey ciphertext

    return li   # Return [Vigenère ciphertext, Autokey ciphertext]

def decrypt(message, key):
    # Vigenère decryption
    message = message.lower()
    encmsg = ""
    for i in range(len(message)):
        numch = chr(((ord(message[i]) - ord(key[i])) % 26) + ord('a'))
        encmsg += numch
    return encmsg

def autodec(message, key):
    # Autokey decryption
    decmsg = ""
    akey = ""
    akey += chr(key + ord('a'))   # Start with initial key character
    for i in range(len(message)):
        num = ord(message[i]) - ord('a')
        num1 = ord(akey[i]) - ord('a')
        numch = chr(((num - num1) % 26) + ord('a'))
        decmsg += numch
        akey += numch   # Extend key with decrypted plaintext
    return decmsg

# --- Main Program ---
message = "the house is being sold tonight"
message = message.replace(" ", "")   # Remove spaces
key = "dollars"

# Autokey generation: start with 'h' (7+'a') then append plaintext except last char
autkey = chr(7 + ord('a'))
autkey += message[:len(message) - 1]

# Encrypt using both Vigenère and Autokey
encmsg = encrypt(message, keymsg(key.lower(), len(message)), autkey)
vigenc, autoenc = encmsg   # Separate the two ciphertexts

# Autokey decryption demo (with starting key = 7, i.e., 'h')
print(autodec(autoenc, 7))


