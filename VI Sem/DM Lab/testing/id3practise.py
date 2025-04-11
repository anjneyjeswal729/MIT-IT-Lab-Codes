import pandas as pd
import numpy as np
from collections import Counter
from math import log2

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

df=pd.DataFrame(data)
features=df.columns[:-1].tolist()
feature = features[1]
# def entropy(target_col):
#     elements, counts = np.unique(target_col, return_counts=True)
#     entropy_value = -np.sum([(count / len(target_col)) * log2(count / len(target_col)) for count in counts])
#     return entropy_value


# def ingain(data,feature,target='PlayTennis'):
#     entr=entropy(data['PlayTennis'])
#     val,coun=np.unique(data[feature],return_counts=True)
#     weientr=np.sum([(coun[i]/np.sum(coun)) * entropy(data.where(data[feature]==val[i]).dropna()[target] )for i in range(len(val))])
#     print(weientr)
def entropy(target):
    ele,count=np.unique(target,return_counts=True)
    entval= -np.sum([(count[i]/len(target))*log2(count[i]/len(target)) for i in range(len(ele))])
    # print(entval)
    return entval

def infogain(data,feature,target='PlayTennis'):
    entr=entropy(data[target])
    val,count=np.unique(data[feature],return_counts=True)
    weiwen=np.sum([
        (count[i]/np.sum(count) * entropy(data.where(data[feature]==val[i]).dropna()[target])) for i in range(len(val))
    ])
    ig=entr-weiwen
    print(ig)
    return ig

entropy(data['PlayTennis'])
maxi=0

for i in features:
    ig=infogain(df,i)
    if maxi<ig:
        maxi=ig
        feature=i
print(feature)
