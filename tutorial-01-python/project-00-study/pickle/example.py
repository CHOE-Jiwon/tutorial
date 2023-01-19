import pickle


# Sample Object to be pickled
class Example:
    def __init__(self, value):
        self.value = value


example_obj = Example(42)

# Save the object to a file
with open('example.pkl', 'wb') as f:
    pickle.dump(example_obj, f)

# Load the object from the file
with open('example.pkl', 'rb') as f:
    loaded_obj = pickle.load(f)

# Print the value of the loaded object
print(loaded_obj.value)
