import sys

n = int(input())
volume_left = 50 * 50 * 50

for i in range(1, n+1):
    dimensions = input()
    
    dimension_list = dimensions.split(' ')
    
    for j in dimension_list:
        if not (1 <= int(j) <= 50):
            print('Case ' + str(i) + ': bad')
            break
    else:
        print('Case ' + str(i) + ': good')
        