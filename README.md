Project Overview

Titanic ML Studio is a standalone desktop application that combines Java (Swing GUI) and Python (Machine Learning backend) to perform data analysis, machine learning, and visualizations on the Titanic dataset.
The aim is to create a full offline desktop solution without relying on web applications like Flask or Django.

Currently, the application provides survival predictions and graph-based data exploration through an interactive Java interface. The backend uses a Logistic Regression model trained on the Titanic dataset.

Current Status
The project is under active development. The following features are currently implemented:

Logistic Regression model is trained on the Titanic dataset.

The model generates essential machine learning graphs:

Confusion Matrix

Actual vs Predicted comparison

Logistic Regression Coefficient Importance

An additional custom graph based on selected factors (such as Survival vs Age, Survival vs Sex, etc.) can be generated dynamically from the Java application.

Java GUI application allows the user to select the graph type and trigger the backend processing.

The application executes Python scripts from Java and displays the resulting graphs inside the GUI itself.

Features Planned (In Progress)
Adding multifactor analysis (example: Age and Sex vs Survival).

Creating a new page in the Java application for manual data input where users can enter Age, Sex, Pclass, and Fare, and receive survival prediction using the trained model.

Enhancing the Java GUI design for better user experience and navigation.

Improving Python backend to handle new prediction requests dynamically.

Future additions like Random Forest model comparison, exporting results to files, and batch predictions for multiple passengers.

How to Run (Current Version)
Clone the repository.

Ensure Python 3.10 or 3.9 is installed along with required libraries:

-pandas

-seaborn

-matplotlib

-scikit-learn

Place train.csv from Kaggle Titanic Dataset into the project folder.

Compile and run the Java GUI (MainApp.java).

Select a graph type and execute to see ML results and visualizations.

Note
Since this project is still being actively built, some features like multifactor analysis and real-time prediction from user input are under development. Regular updates will be pushed as the project progresses.