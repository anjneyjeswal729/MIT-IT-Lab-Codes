'''Given an ElGamal encryption scheme with a public key (p, g, h) and a private key
 x, encrypt the message "Confidential Data". Then decrypt the ciphertext to retrieve
 the original message.'''

import random

def gcd(a, b):
    # Compute greatest common divisor using Euclidean algorithm
    if a < b:
        return gcd(b, a)
    elif a % b == 0:
        return b
    else:
        return gcd(b, a % b)

def gen_key(q):
    # Generate a random key coprime with q
    key = random.randint(10**20, q)
    while gcd(q, key) != 1:
        key = random.randint(10**20, q)
    return key

def power(a, b, c):
    # Compute (a^b) % c efficiently using modular exponentiation
    x = 1
    y = a
    while b > 0:
        if b % 2 != 0:
            x = (x * y) % c
        y = (y * y) % c
        b = int(b / 2)
    return x % c

def encrypt(msg, q, h, g):
    # Encrypt message using ElGamal scheme
    en_msg = []
    k = gen_key(q)          # Random ephemeral key for this encryption
    s = power(h, k, q)      # Shared secret
    p = power(g, k, q)      # Component sent with ciphertext
    for i in range(len(msg)):
        en_msg.append(msg[i])
    print("g^k used:", p)
    print("g^ak used:", s)
    # Encrypt each character
    for i in range(len(en_msg)):
        en_msg[i] = s * ord(en_msg[i])
    return en_msg, p

def decrypt(en_msg, p, key, q):
    # Decrypt message using receiver's private key
    dr_msg = []
    h = power(p, key, q)    # Compute shared secret
    for i in range(len(en_msg)):
        dr_msg.append(chr(int(en_msg[i] / h)))  # Recover original char
    return "".join(dr_msg)

def main():
    msg = "Confidential Data"
    print("Original Message:", msg)

    # Generate large prime-like number q and generator g
    q = random.randint(10**20, 10**50)
    g = random.randint(2, q)

    key = gen_key(q)        # Receiver's private key
    h = power(g, key, q)    # Receiver's public key component

    print("g used:", g)
    print("g^a used:", h)

    # Encrypt and decrypt
    en_msg, p = encrypt(msg, q, h, g)
    dr_msg = decrypt(en_msg, p, key, q)
    print("Decrypted Message:", dr_msg)

if __name__ == "__main__":
    main()

