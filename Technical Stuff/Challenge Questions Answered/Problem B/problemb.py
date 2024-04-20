import sys
language_dict = {
    'HELLO' : 'ENGLISH',
    'HALLO' : 'AFRIKAANS',
    'BONJOUR' : 'FRENCH',
    'MOLO' : 'ISIXHOSA',
    'SAWUBONA' : 'ISIZULU',
    'MARHABA' : 'ARABIC'
}
    

line = input()

while line != '#':
    if line in language_dict.keys():
        print(language_dict[line])
    else:
        print('UNKNOWN')
        
    line = input()
else:
    exit()
    
         