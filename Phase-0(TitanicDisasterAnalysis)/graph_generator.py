import sys
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import confusion_matrix, accuracy_score

import os

# Ensure output folder exists
os.makedirs("output", exist_ok=True)

# Load dataset
df = pd.read_csv("train.csv")
df["Age"].fillna(df["Age"].median(), inplace=True)
df["Fare"].fillna(df["Fare"].median(), inplace=True)
df["Sex"] = df["Sex"].map({"male": 0, "female": 1})
df = df[["Pclass", "Sex", "Age", "Fare", "Survived"]]

# Prepare features and target
X = df.drop("Survived", axis=1)
y = df["Survived"]

# Train-test split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Train model
model = LogisticRegression()
model.fit(X_train, y_train)
y_pred = model.predict(X_test)

# 1. Confusion Matrix
plt.figure(figsize=(5, 4))
sns.heatmap(confusion_matrix(y_test, y_pred), annot=True, fmt='d', cmap='Blues',
            xticklabels=["Not Survived", "Survived"], yticklabels=["Not Survived", "Survived"])
plt.title("Confusion Matrix")
plt.savefig("output/confusion_matrix.png")
plt.close()

# 2. Actual vs Predicted
comparison = pd.DataFrame({"Actual": y_test.values, "Predicted": y_pred})
plt.figure(figsize=(10, 4))
comparison.head(20).plot(kind='bar', colormap="Set2")
plt.title("Actual vs Predicted (First 20)")
plt.savefig("output/actual_vs_predicted.png")
plt.close()

# 3. Coefficients
coeffs = pd.Series(model.coef_[0], index=X.columns)
plt.figure(figsize=(6, 4))
sns.barplot(x=coeffs.values, y=coeffs.index, palette="viridis")
plt.title("Logistic Regression Coefficients")
plt.axvline(0, color="gray", linestyle="--")
plt.savefig("output/coefficients.png")
plt.close()

# 4. Extra Graph from Java
extra = sys.argv[1] if len(sys.argv) > 1 else "None"

plt.figure(figsize=(7, 4))
sns.set(style="whitegrid")

# Combine features and target into one DataFrame
plot_df = X.copy()
plot_df["Survived"] = y

if extra == "Survival vs Age":
    # Average Survival Rate by Age Groups
    plot_df["AgeGroup"] = pd.cut(plot_df["Age"], bins=[0, 10, 20, 30, 40, 50, 60, 80])
    survival_by_age = plot_df.groupby("AgeGroup")["Survived"].mean().reset_index()
    sns.barplot(x="AgeGroup", y="Survived", data=survival_by_age)
    plt.xticks(rotation=45)
    plt.ylabel("Survival Rate")

elif extra == "Survival vs Sex":
    survival_by_sex = plot_df.groupby("Sex")["Survived"].mean().reset_index()
    survival_by_sex["Sex"] = survival_by_sex["Sex"].map({0: "Male", 1: "Female"})
    sns.barplot(x="Sex", y="Survived", data=survival_by_sex)
    plt.ylabel("Survival Rate")

elif extra == "Survival vs Fare":
    plot_df["FareGroup"] = pd.qcut(plot_df["Fare"], 5)
    survival_by_fare = plot_df.groupby("FareGroup")["Survived"].mean().reset_index()
    sns.barplot(x="FareGroup", y="Survived", data=survival_by_fare)
    plt.xticks(rotation=45)
    plt.ylabel("Survival Rate")

elif extra == "Survival vs Pclass":
    survival_by_class = plot_df.groupby("Pclass")["Survived"].mean().reset_index()
    sns.barplot(x="Pclass", y="Survived", data=survival_by_class)
    plt.ylabel("Survival Rate")

plt.title(extra)
plt.tight_layout()
plt.savefig("output/extra_plot.png")
plt.close()
