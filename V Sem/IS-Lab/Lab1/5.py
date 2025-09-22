'''John is reading a mystery book involving cryptography. In one part of the book, the
 author gives a ciphertext "CIW" and two paragraphs later the author tells the reader that
 this is a shift cipher and the plaintext is "yes". In the next chapter, the hero found a tablet
 in a cave with "XVIEWYWI" engraved on it. John immediately found the actual meaning
 of the ciphertext.  Identify the type of attack and plaintext. '''

def findkey(encmsg,pt):
    key=ord(encmsg[0])-ord(pt[0])
    return key%26

def findmsg(encmsg,key):
    pt=""
    for i in encmsg:
        num=ord(i)-ord('a')
        num=(num-key)%26
        pt+=chr(num+ord('a'))
    return pt
        

encmsg="ciw"
pt="yes"
newencmsg="xviewywi"
print(findkey(encmsg,pt),findmsg(newencmsg,4))
