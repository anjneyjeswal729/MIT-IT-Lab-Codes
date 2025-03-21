import pandas as pd
import math

def calculate_entropy(data):
    class_counts = data['Class'].value_counts()
    total = len(data)
    entropy = 0
    for count in class_counts:
        probability = count / total
        entropy -= probability * math.log2(probability)
    return entropy

def calculate_information_gain(data, attribute):
    total_entropy = calculate_entropy(data)
    
    attribute_values = data[attribute].value_counts()
    
    weighted_entropy = 0
    for value in attribute_values.index:
        subset = data[data[attribute] == value]
        subset_entropy = calculate_entropy(subset)
        weighted_entropy += (len(subset) / len(data)) * subset_entropy
    
    return total_entropy - weighted_entropy

def select_best_attribute(data, attributes):
    best_attribute = None
    best_info_gain = -float('inf') 
    
    for attribute in attributes:
        info_gain = calculate_information_gain(data, attribute)
        if info_gain > best_info_gain:
            best_info_gain = info_gain
            best_attribute = attribute
    
    return best_attribute

def build_decision_tree(data, attributes):
    if len(data['Class'].unique()) == 1:
        return data['Class'].iloc[0]
    
    if not attributes:
        return data['Class'].mode()[0]
    
    best_attribute = select_best_attribute(data, attributes)
    
    tree = {best_attribute: {}}
    
    attribute_values = data[best_attribute].unique()
    
    for value in attribute_values:
        subset = data[data[best_attribute] == value]
        tree[best_attribute][value] = build_decision_tree(subset, [attr for attr in attributes if attr != best_attribute])
    
    return tree

def predict(tree, instance):
    if isinstance(tree, str):
        return tree
    
    attribute = list(tree.keys())[0]
    attribute_value = instance[attribute]
    
    return predict(tree[attribute][attribute_value], instance)

data = {
    'Outlook': ['Sunny', 'Sunny', 'Overcast', 'Rain', 'Rain', 'Rain', 'Overcast', 'Sunny', 'Sunny', 'Rain', 'Sunny', 'Overcast', 'Overcast', 'Rain'],
    'Temperature': ['Hot', 'Hot', 'Hot', 'Mild', 'Cool', 'Cool', 'Cool', 'Mild', 'Cool', 'Mild', 'Mild', 'Mild', 'Hot', 'Mild'],
    'Humidity': ['High', 'High', 'High', 'High', 'High', 'Low', 'Low', 'High', 'Low', 'Low', 'Low', 'High', 'Low', 'High'],
    'Wind': ['Weak', 'Strong', 'Weak', 'Weak', 'Weak', 'Weak', 'Strong', 'Weak', 'Weak', 'Weak', 'Strong', 'Strong', 'Weak', 'Strong'],
    'Class': ['No', 'No', 'Yes', 'Yes', 'Yes', 'No', 'Yes', 'No', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'No']
}

df = pd.DataFrame(data)

attributes = ['Outlook', 'Temperature', 'Humidity', 'Wind']

tree = build_decision_tree(df, attributes)

print("Decision Tree:")
print(tree)

sample_instance = {'Outlook': 'Sunny', 'Temperature': 'Hot', 'Humidity': 'High', 'Wind': 'Weak'}
prediction = predict(tree, sample_instance)
print(f"\nPrediction for {sample_instance}: {prediction}")
