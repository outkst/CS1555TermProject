months = ["JAN", "FEB", "MAR", "APR", "MAY"]
i = 0
j = 0
g_id = 1
myf = open('insert.txt','w')
with open("maleNames.txt") as f:
    for line in f:
        content = line.strip().split(" ")
        print(content)
        myf.write("INSERT INTO USERS VALUES( {}, \'{}\' , \'{}\', \'{}{}@gmail.com\', TO_DATE(\'{}-{}-90\',\'DD-MON-YY\'), current_timestamp);\n".format(g_id, content[0], content[1], content[0][0].lower(), content[1].lower(), i+1, months[j]))
        i = (i + 1 ) % 28
        g_id += 1
        if i == 27:
            j += 1

with open("femaleNames.txt") as f:
    for line in f:
        content = line.strip().split(" ")
        print(content)
        myf.write("INSERT INTO USERS VALUES( {}, \'{}\' , \'{}\', \'{}{}@gmail.com\', TO_DATE(\'{}-{}-90\',\'DD-MON-YY\'), current_timestamp);\n".format(g_id, content[0], content[1], content[0][0].lower(), content[1].lower(), i+1, months[j]))
        i = (i + 1 ) % 28
        g_id += 1
        if i == 27:
            j += 1
myf.close()
