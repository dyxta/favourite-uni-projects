# Dylan Tasdhary
# 17 May 2021
import string
num_words = int(input())

matches = 0

for i in range(num_words):
    line = input()
    
    if len(line) > 3:
        print('3')
        continue
    matches = 0
    
    for i in line:
        if line[0] == 'o':
            matches += 1
            continue
        if line[1] == 'n':
            matches += 1
            continue
        if line[2] == 'e':
            matches +=1
            continue
        
    if matches > 1:
        print('1')
    else:
        print('2')
             