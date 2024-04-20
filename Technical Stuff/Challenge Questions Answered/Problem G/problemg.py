test_case_num = int(input())

for i in range(test_case_num):
    days = int(input())
    parties_num = int(input())
    parties_param = []
    
    for i in range(parties_num):
        parties_param.append(int(input()))    
    
    protest_day = []
    week_no = 0
    
    for day in range(1,days+1):
        if (day+1)%7 == 0 or day%7==0:
            continue
        
        for j in range(parties_num):
            if day % parties_param[j] == 0 and day not in protest_day:
                protest_day.append(day)          
    print(len(protest_day))
        
        