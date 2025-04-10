from itertools import combinations
from collections import defaultdict


def scan_database(dataset, candidates, min_support_count):
    itemset_count = defaultdict(int)

    for transaction in dataset:
        for candidate in candidates:
            if candidate.issubset(transaction):
                itemset_count[candidate] += 1

    frequent_itemsets = {}
    for itemset, count in itemset_count.items():
        if count >= min_support_count:
            frequent_itemsets[itemset] = count

    return frequent_itemsets


def generate_candidates(frequent_itemsets):
    candidates = set()
    itemsets = list(frequent_itemsets.keys())
    num_itemsets = len(itemsets)

    for i in range(num_itemsets):
        for j in range(i + 1, num_itemsets):
            candidate = itemsets[i].union(itemsets[j])
            if len(candidate) == len(itemsets[i]) + 1:
                candidates.add(candidate)

    return candidates


def generate_association_rules(frequent_itemsets, min_confidence):
    rules = []

    for itemset in frequent_itemsets:
        if len(itemset) < 2:
            continue  # no rule can be formed

        # Generate all non-empty proper subsets
        for i in range(1, len(itemset)):
            for subset in combinations(itemset, i):
                antecedent = frozenset(subset)
                consequent = itemset - antecedent

                if len(consequent) == 0:
                    continue

                support_count = frequent_itemsets[itemset]
                antecedent_support_count = frequent_itemsets.get(antecedent, 0)

                if antecedent_support_count > 0:
                    confidence = support_count / antecedent_support_count
                    if confidence >= min_confidence:
                        rules.append((antecedent, consequent, confidence))

    return rules



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

min_support_count = 2
min_confidence = 0.75

C1 = [frozenset([item]) for transaction in dataset for item in transaction]
C1 = set(C1)

L1 = scan_database(dataset, C1, min_support_count)

L = [L1]
k = 2
while True:
    Ck = generate_candidates(L[k - 2])
    Lk = scan_database(dataset, Ck, min_support_count)

    if not Lk:
        break

    L.append(Lk)
    k += 1

frequent_itemsets = {}
for level in L:
    frequent_itemsets.update(level)

association_rules = generate_association_rules(frequent_itemsets, min_confidence)

print("Frequent Itemsets:")
for itemset, count in frequent_itemsets.items():
    print(f"{set(itemset)}: {count} occurrences")

print("\nAssociation Rules:")
for rule in association_rules:
    print(f"{set(rule[0])} -> {set(rule[1])} with confidence = {rule[2]:.2f}")