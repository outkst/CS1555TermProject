from random import randint

start_rand = 1
end_rand = 10
user_id = randint(start_rand,end_rand)
friend_id = randint(start_rand,end_rand)
count = 0

myf = open('friendInsert.txt','w')

while count < 1000:
    while user_id == friend_id:
        user_id = randint(start_rand,end_rand)
        friend_id = randint(start_rand,end_rand)

    myf.write("INSERT INTO FRIENDSHIPS (USERID, FRIENDID) VALUES ({}, {});\n".format(user_id, friend_id))
    user_id = randint(start_rand,end_rand)
    friend_id = randint(start_rand,end_rand)
    count += 1
    if count % 100 == 0 :
        start_rand += 10
        end_rand += 10

myf.close()
