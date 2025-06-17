matrix = [[1, 2], [3, 4]]

flat1 = [num for row in matrix for num in row]  
# [1, 2, 3, 4] (先左后右)

flat2 = [(num + 10 for num in row) for row in matrix]
print(flat1)
print(flat2)