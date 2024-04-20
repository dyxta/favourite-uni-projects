# Dylan Tasdhary 2021
# Determining if a fuse will blow given a number of devices, their consumption 
# and the sequence they are interacted with

line = input() # Receiving input
seq_num = 0 # Keeping track of number of sequences

while line != '0 0 0': # This test case terminates input
    seq_num += 1 
    flag = False
    var_list = line.split(' ') # Breaking n, m, c apart from input
    
    
    n, m ,c = int(var_list[0]), int(var_list[1]), int(var_list[2])
        
    d_consump_list = [] # Creating an empty list for the consumptions of each device
    d_state = [] # Empty list for the state of device (ON/OFF <1/0>)
    for device_consumption in range(n): 
            consumption = int(input()) # consumption of devices n lines after beginning of test case
            d_state.append(0) # appending 0, n elements or n devices
            d_consump_list.append(consumption) # consumption of n device
            
    current_consump = 0 # the beginning starts off with 0 A
    current_max = 0 # Running variable to track the max
 
    for i in range(m): # looping m lines to get sequence of device interactions
        device = int(input())
        
        if d_state[device -1] == 0: # checking if the state of device at index device-1
            d_state[device-1] = 1 # if it was off, now turn it on 
            current_consump += d_consump_list[device-1] # adding to current_consumption
            
            if current_consump > c: # just checking if fuse would have been blown
                flag = True
                
            else:
                if current_consump > current_max:
                    current_max = current_consump
        else:
            d_state[device-1] = 0
            current_consump -= d_consump_list[device-1]
            
    else:
        print('Sequence', seq_num)
        if flag == True:
            print('Fuse was blown.')        
        
        else:
            print('Fuse was not blown.')
            print('Maximal power consumption was', current_max, 'amperes.')
    
    print()
    line = input()

    