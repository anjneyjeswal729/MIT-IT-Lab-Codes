from itertools import combinations
from collections import defaultdict



dataset = [
    {'A', 'B', 'E'},
    {'B', 'D'},
    {'B', 'C'},
    {'A', 'B', 'D'},
    {'A', 'C'},
    {'B', 'C'},
    {'A', 'C'},
    {'A', 'B', 'C', 'E'},
    {'A', 'B', 'C'}
]
minsup=2
def candidate1gen():
    c = defaultdict(int)
    frequent_items ={}
    frequent = []

    for trans in dataset:
        for item in trans:
            c[item] += 1  # defaultdict handles missing keys

    for item, count in c.items():
        if count >= minsup:
            frequent_items[item]=count
    
    for k in frequent_items:
        print(set(k),frequent_items[k])
    # print(frequent_items.items())
    return frequent_items

    
l1=candidate1gen()
def candidate2gen():
    c2=set()
    c = defaultdict(int)
    frequent_items ={}
    for i in l1:
        for j in l1:
            if i!=j:
                c2.add(frozenset(i).union(frozenset(j)))
            
    for trans in dataset:
        for k in c2:
            if k.issubset(trans):
                c[k]+=1
    for ck,count in c.items():
        if count>=minsup:
            frequent_items[ck]=count
        
    for k in frequent_items:
        print(set(k),frequent_items[k])

candidate2gen()
