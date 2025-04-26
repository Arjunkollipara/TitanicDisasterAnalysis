import sys
import pandas as pd
from sklearn.linear_model import LogisticRegression
import pickle

# Load the trained model
model = pickle.load(open("titanic_model.pkl", "rb"))

# Get user input from the command-line arguments
age = float(sys.argv[1])
sex = 1 if sys.argv[2].lower() == "female" else 0
fare = float(sys.argv[3])

# Prepare the input for prediction
input_data = pd.DataFrame([[age, sex, fare]], columns=["Age", "Sex", "Fare"])

# Predict survival
prediction = model.predict(input_data)[0]
survival_status = "Survived" if prediction == 1 else "Not Survived"

# Output prediction result
with open("output/prediction_result.txt", "w") as f:
    f.write(f"Prediction: {survival_status}\n")
