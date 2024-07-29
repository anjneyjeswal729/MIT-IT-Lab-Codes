def keymsg(jk,leng):
    key=jk
    while len(key)<leng:
        key+=jk
    key=key[:leng]
    return key 

def encrypt(message,key,autokey):
    message=message.lower()
    autkey=chr(autokey+ord('a'))
    autkey+=message[:len(message)-1]
    li=[]
    encmsg=""
    for i in range(len(message)):
        num=ord(message[i])-ord('a')
        num1=ord(key[i])-ord('a')
        numch=chr(((num+num1)%26)+ord('a'))
        encmsg+=numch
    li.append(encmsg)
    encmsg=""
    for i in range(len(message)):
        num=ord(message[i])-ord('a')
        num1=ord(autkey[i])-ord('a')
        numch=chr(((num+num1)%26)+ord('a'))
        encmsg+=numch
    li.append(encmsg)
    return li

def decrypt(message,key,autokey):
    message=message.lower()
    encmsg=""
    for i in range(len(message)):
        numch=chr(((ord(message[i])-ord(key[i]))%26)+ord('a'))
        encmsg+=numch
    return encmsg
    
message="the house is being sold tonight"
message=message.replace(" ","")
key="dollars"

encmsg=encrypt(message,keymsg(key.lower(),len(message)),7)
print(encmsg)
# decmsg=decrypt(encmsg,keymsg(key.lower(),len(message)),7)
# print(decmsg)

    