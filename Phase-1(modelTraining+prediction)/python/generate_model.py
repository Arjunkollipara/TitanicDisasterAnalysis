import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import pickle
import os

# Ensure output folder exists
output_dir = "../java_app/output/"
os.makedirs(output_dir, exist_ok=True)

# Load the dataset
df = pd.read_csv("train.csv")
df["Age"].fillna(df["Age"].median(), inplace=True)
df["Fare"].fillna(df["Fare"].median(), inplace=True)
df["Sex"] = df["Sex"].map({"male": 0, "female": 1})

# Prepare features and target
X = df[["Pclass", "Sex", "Age", "Fare"]]
y = df["Survived"]

# Load the trained model
with open(f"{output_dir}trained_model.pkl", "rb") as f:
    model = pickle.load(f)

# 1. Confusion Matrix
from sklearn.metrics import confusion_matrix
y_pred = model.predict(X)
conf_matrix = confusion_matrix(y, y_pred)
plt.figure(figsize=(6, 4))
sns.heatmap(conf_matrix, annot=True, fmt="d", cmap="Blues",
            xticklabels=["Not Survived", "Survived"], yticklabels=["Not Survived", "Survived"])
plt.title("Confusion Matrix")
plt.savefig(f"{output_dir}confusion_matrix.png")
plt.close()

# 2. Actual vs Predicted
comparison = pd.DataFrame({"Actual": y, "Predicted": y_pred})
plt.figure(figsize=(10, 4))
comparison.head(20).plot(kind='bar', colormap="Set2")
plt.title("Actual vs Predicted (First 20)")
plt.savefig(f"{output_dir}actual_vs_predicted.png")
plt.close()

# 3. Coefficients
coeffs = pd.Series(model.coef_[0], index=X.columns)
plt.figure(figsize=(6, 4))
sns.barplot(x=coeffs.values, y=coeffs.index, palette="viridis")
plt.title("Logistic Regression Coefficients")
plt.axvline(0, color="gray", linestyle="--")
plt.savefig(f"{output_dir}coefficients.png")
plt.close()
