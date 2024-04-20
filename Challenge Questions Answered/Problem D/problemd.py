line = input()
 
while line != '-1':
    var_list = line.split(' ')
    A, B, N = int(var_list[0]), int(var_list[1]), int(var_list[2])
    
    for i in range(1, N+1):
        if (A % i) == (B % i):
            max_route = i
    else:
        print(max_route)
    line = input()