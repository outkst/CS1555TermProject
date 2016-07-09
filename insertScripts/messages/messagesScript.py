from random import randint
g_id = 1
sender_id = randint(1,100)
recip_id = randint(1,100)
recip_count = 0
group_id = randint(1,10)

textInsert = open('textInsert.txt', 'w')
with open('textMessage.txt') as f:
    for line in f:
        content = line.strip().split('. ')
        for c in content:
            print g_id, ' ',c.split(' ', 1)[0].upper(), ' ', c
            if recip_count <= 250:
                while sender_id == recip_id:
                    sender_id = randint(1,100)
                    recip_id = randint(1,100)

                textInsert.write("INSERT INTO MESSAGES VALUES({},{}, \'{}\', \'{}\', {}, NULL, current_timestamp);\n".format(g_id, sender_id, c.split(' ', 1)[0].upper(), c, recip_id  ))
                recip_count += 1
                sender_id = randint(1,100)
                recip_id = randint(1,100)
            else:
                textInsert.write("INSERT INTO MESSAGES VALUES({},{}, \'{}\', \'{}\', NULL, {}, current_timestamp);\n".format(g_id, sender_id, c.split(' ', 1)[0].upper(), c, group_id))
                group_id = randint(1,10)

            g_id += 1
textInsert.close()
