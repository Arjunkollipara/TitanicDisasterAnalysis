import pickle
import pandas as pd

# Load the trained model
with open("trained_model.pkl", "rb") as f:
    model = pickle.load(f)

# Function to predict if a person survives or not
def predict_survival(pclass, sex, age, fare):
    # Prepare the input features
    features = pd.DataFrame([[pclass, sex, age, fare]], columns=["Pclass", "Sex", "Age", "Fare"])
    
    # Make prediction
    prediction = model.predict(features)[0]
    return "Survived" if prediction == 1 else "Not Survived"

# Input data from user
print("Enter the details to predict survival:")
pclass = int(input("Pclass (1, 2, or 3): "))
sex = int(input("Sex (0 for Male, 1 for Female): "))
age = float(input("Age: "))
fare = float(input("Fare: "))

# Call the prediction function
result = predict_survival(pclass, sex, age, fare)

# Display the result
print(f"The prediction is: {result}")
