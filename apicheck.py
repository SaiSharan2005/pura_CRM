import requests

# Define the API URL
url = "http://localhost:8005/api/auth/login"

# Define the payload
payload = {
    "username": "manager",
    "password": "Test@123"
}

# Define the headers (optional, if your API requires specific headers)
headers = {
    "Content-Type": "application/json"
}

# Make the POST request
try:
    response = requests.post(url, json=payload, headers=headers)

    # Check if the response was successful
    if response.status_code == 200:
        print("Login successful!")
        print("Response JSON:", response.json())  # Prints the token or other response data
    else:
        print(f"Login failed with status code {response.status_code}")
        print("Response:", response.text)  # Prints the error message from the server

except requests.exceptions.RequestException as e:
    print("An error occurred:", e)
