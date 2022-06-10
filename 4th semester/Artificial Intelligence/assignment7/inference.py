import torch

# import model

# from myDatabase import get_function_value

# we load the model
from MyModel import Net
from myDatabase import get_function_value

filepath = "myNet.pt"
ann = Net(2, 64, 1)

ann.load_state_dict(torch.load(filepath))
ann.eval()

# visualise the parameters for the ann (aka weights and biases)
# for name, param in ann.named_parameters():
#     if param.requires_grad:
#         print (name, param.data)


x1 = float(input("x1 = "))
x1 = torch.tensor([x1])
x2 = float(input("x2 = "))
x2 = torch.tensor([x2])
print(ann(torch.column_stack((x1, x2))).tolist())
print(get_function_value(x1, x2))
