Q1

import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split

# Load the dataset
url = "https://raw.githubusercontent.com/jbrownlee/Datasets/master/pima-indians-diabetes.data.csv"
columns = ['Pregnancies', 'Glucose', 'BloodPressure', 'SkinThickness', 'Insulin', 'BMI', 'DiabetesPedigreeFunction',
           'Age', 'Outcome']
data = pd.read_csv(url, header=None, names=columns)

# Split the data
X = data.drop('Outcome', axis=1)
y = data['Outcome']
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)


# Gaussian Naive Bayes Classifier Implementation
class GaussianNaiveBayes:
    def fit(self, X, y):
        self.classes = np.unique(y)
        self.means = {}
        self.stds = {}
        self.priors = {}

        for cls in self.classes:
            X_cls = X[y == cls]
            self.means[cls] = X_cls.mean()
            self.stds[cls] = X_cls.std()
            self.priors[cls] = len(X_cls) / len(X)

    def gaussian_pdf(self, x, mean, std):
        exponent = np.exp(- ((x - mean) ** 2) / (2 * std ** 2))
        return (1 / (np.sqrt(2 * np.pi) * std)) * exponent

    def predict(self, X):
        predictions = []
        for _, row in X.iterrows():
            posteriors = {}
            for cls in self.classes:
                prior = np.log(self.priors[cls])
                likelihood = np.sum(np.log(self.gaussian_pdf(row, self.means[cls], self.stds[cls])))
                posteriors[cls] = prior + likelihood
            predictions.append(max(posteriors, key=posteriors.get))
        return np.array(predictions)


# Train the model
gnb = GaussianNaiveBayes()
gnb.fit(X_train, y_train)

# Make predictions
y_pred = gnb.predict(X_test)

# Calculate Accuracy
accuracy = np.mean(y_pred == y_test)
print(f"Accuracy of Gaussian Naive Bayes: {accuracy * 100:.2f}%")
