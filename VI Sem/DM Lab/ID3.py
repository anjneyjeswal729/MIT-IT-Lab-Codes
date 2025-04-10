import pandas as pd
import numpy as np
from collections import Counter
from math import log2

# Sample Dataset
data = {
    'Outlook': ['Sunny', 'Sunny', 'Overcast', 'Rain', 'Rain', 'Rain', 'Overcast', 'Sunny', 'Sunny', 'Rain', 'Sunny',
                'Overcast', 'Overcast', 'Rain'],
    'Temperature': ['Hot', 'Hot', 'Hot', 'Mild', 'Cool', 'Cool', 'Cool', 'Mild', 'Cool', 'Mild', 'Mild', 'Mild', 'Hot',
                    'Mild'],
    'Humidity': ['High', 'High', 'High', 'High', 'Normal', 'Normal', 'Normal', 'High', 'Normal', 'Normal', 'Normal',
                 'High', 'Normal', 'High'],
    'Wind': ['Weak', 'Strong', 'Weak', 'Weak', 'Weak', 'Strong', 'Strong', 'Weak', 'Weak', 'Weak', 'Strong', 'Strong',
             'Weak', 'Strong'],
    'PlayTennis': ['No', 'No', 'Yes', 'Yes', 'Yes', 'No', 'Yes', 'No', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'No']
}

df = pd.DataFrame(data)


# Function to calculate entropy
def entropy(target_col):
    elements, counts = np.unique(target_col, return_counts=True)
    entropy_value = -np.sum([(count / len(target_col)) * log2(count / len(target_col)) for count in counts])
    return entropy_value


# Function to calculate information gain
def information_gain(data, feature, target_name='PlayTennis'):
    total_entropy = entropy(data[target_name])
    values, counts = np.unique(data[feature], return_counts=True)
    weighted_entropy = np.sum(
        [(counts[i] / np.sum(counts)) * entropy(data.where(data[feature] == values[i]).dropna()[target_name]) for i in
         range(len(values))])
    info_gain = total_entropy - weighted_entropy
    return info_gain


# ID3 Algorithm
def id3(data, originaldata, features, target_attribute_name='PlayTennis', depth=0, max_depth=None):
    # Base cases
    if len(np.unique(data[target_attribute_name])) == 1:
        return np.unique(data[target_attribute_name])[0]
    if len(features) == 0 or (max_depth and depth == max_depth):
        return Counter(data[target_attribute_name]).most_common(1)[0][0]

    # Selecting the best feature based on information gain
    info_gains = [information_gain(data, feature, target_attribute_name) for feature in features]
    best_feature_index = np.argmax(info_gains)
    best_feature = features[best_feature_index]

    # Creating the tree
    tree = {best_feature: {}}
    features = [f for f in features if f != best_feature]

    for value in np.unique(data[best_feature]):
        sub_data = data.where(data[best_feature] == value).dropna()
        subtree = id3(sub_data, data, features, target_attribute_name, depth + 1, max_depth)
        tree[best_feature][value] = subtree

    return tree


# Training the decision tree
features = df.columns[:-1].tolist()
tree = id3(df, df, features)


# Function to classify new samples
def classify(tree, sample):
    if isinstance(tree, dict):
        for feature, sub_tree in tree.items():
            value = sample[feature]
            if value in sub_tree:
                return classify(sub_tree[value], sample)
            else:
                return None
    else:
        return tree


# Display the Decision Tree
print("Decision Tree:")
print(tree)

# Classify new samples
new_samples = [
    {'Outlook': 'Sunny', 'Temperature': 'Cool', 'Humidity': 'Normal', 'Wind': 'Strong'},
    {'Outlook': 'Rain', 'Temperature': 'Mild', 'Humidity': 'Normal', 'Wind': 'Strong'}
]

for sample in new_samples:
    prediction = classify(tree, sample)
    print(f"\nNew Sample: {sample}")
    print(f"Predicted Class: {prediction}")



