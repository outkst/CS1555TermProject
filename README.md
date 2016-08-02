# CS1555TermProject

**Term Project for Pitt CS1555 Database Management Systems (Summer 2016).**

[CS1555 Website](https://people.cs.pitt.edu/~nlf4/cs1555/)  *|* [Recitation Website](https://people.cs.pitt.edu/~weg21/1555.html)

A full list of this project's requirements can be viewed in **_1555_term_project.pdf_**

### Milestone 0: Assemble Teams (Due: July 6 @ 11:59PM)

1. [X] Add *nfarnan* to the contributors for the repository.  
2. [X] Add *weg21* to the contributors for the repository.  
3. [X] Add all team members (name, pitt username, github username) to **_team_members.txt_**.  
4. [X] Create a commit labeled "**Milestone 0 submission**" and push it to the repository.  
5. [X] Send an email to nlf4@pitt.edu and weg21@pitt.edu:  
  * [X] The email title should be "**[CS1555] Project milestone 0 submission**".  
  * [X] Insert a link to the repository.  
  * [X] Insert the GitHub commit ID (40-character string / SHA-1 hash).  

### Milestone 1: The '*FaceSpace*' Database Schema & Example Data (Due: July 11 @ 11:59PM)

1. [X] Database Schema Overview:  
  * [X] Choose appropriate data types to make up the attributes of each relation.  
  * [X] Define all structural and semantic integrity constraints.  
  * [X] All assumptions must be stated using comments within the database creation script.  
  * [X] Messages will be constrained to be less than 100 characters.  
2. [X] Database Schema  
  * [X] **Users**  
    * [X] **ID** *number(10)* **PK**  
    * [X] **FName** *varchar2(50)* **NOT NULL**  
    * [X] **LName** *varchar2(50)* **NOT NULL**  
    * [X] **Email** *varchar2(80)* **NOT NULL**  
    * [X] **DOB** *date* **NOT NULL**  
    * [X] **LastLogin** *timestamp*  
    * [X] **DateCreated** *timestamp* **NOT NULL**  
  * [X] **Friendships**  
    * [X] **UserID** *number(10)* **FK Users(ID) NOT NULL**  
    * [X] **FriendID** *number(10)* **FK Users(ID) NOT NULL**  
    * [X] **Approved** *number(1)*  
    * [X] **DateApproved** *timestamp*  
  * [X] **Groups**  
    * [X] **ID** *number(10)* **PK**  
    * [X] **Name** *varchar2(100)* **NOT NULL**  
    * [X] **Description** *varchar2(100)*  
    * [X] **Limit** *number(10)*  
    * [X] **DateCreated** *timestamp* **NOT NULL**  
  * [X] **GroupMembers**  
    * [X] **GroupID** *number(10)* **FK Groups(ID) NOT NULL**  
    * [X] **UserID** *number(10)* **FK Users(ID) NOT NULL**  
    * [X] **DateJoined** *timestamp* **NOT NULL**  
  * [X] **Messages**  
    * [X] **ID** *number(20)* **PK**  
    * [X] **SenderID** *number(10)* **FK Users(ID) NOT NULL**  
    * [X] **Subject** *varchar2(30)*    **NOT NULL**  
    * [X] **Body** *varchar2(100)*      **NOT NULL**  
    * [X] **RecipientID** *number(10)*  **FK Users(ID) NOT NULL**  
    * [X] **DateCreated** *timestamp* **NOT NULL**  
3. [X] EXAMPLE DATA  
  * [X] 100+ Users  
  * [X] 200+ Friendships  
  * [X] 10+ Groups  
  * [X] 300+ Messages  
4. [X] Create a commit labeled "**Milestone 1 submission**" and push it to the repository.  
5. [X] Send an email to nlf4@pitt.edu and weg21@pitt.edu:  
  * [X] The email title should be "**[CS1555] Project milestone 1 submission**".  
  * [X] Insert a link to the repository.  
  * [X] Insert the GitHub commit ID (40-character string / SHA-1 hash).  

### Milestone 2: A JDBC Application To Manage '*FaceSpace*' (Due: July 24 @ 11:59PM)

1. [X] Create a '**FaceSpace**' Java application:  
  * [X] Must interface the Pitt Oracle server (*unixs.cis.pitt.edu*).  
  * [X] Must use the Java Database Connectivity (JDBC) API.  
  * [X] All Tasks must check for, and properly react, to any errors reported by the DBMS.  
  * [X] All Tasks must provide appropriate *SUCCESS* or *FAILURE* feedback to the user.  
  * [X] Transactions must be defined appropriately:  
    * [X] Design all SQL transactions only when necessary.  
    * [X] Use concurrency control mechanisms supported by Oracle to ensure inconsistent states do not occur:  
      * Isolation Level  
      * Locking Modes  
    * [X] Assume multiple requests can be made at the same time, on behalf of multiple users.  
  * [X] Implement the following functions:  
    * [X] **_createUser_**
      * Given a **name**, **email address**, and **date of birth**, add a new user to the system.  
    * [X] **_initiateFriendship_**
      * Create a pending friendship from one user to another.  
    * [X] **_establishFriendship_**
      * Create a bilateral friendship between two users.  
    * [X] **_displayFriends_**
      * Given a **user**, look up all of that user's establish and pending friendships. Print out this information in a nicely formatted way.  
    * [X] **_createGroup_**
      * Given a **name**, **description**, and **membership limit**, add a new group to the system.  
    * [X] **_addToGroup_**
      * Given a **user** and a **group**, add the user to the group so long as that would not violate the group's membership limit.  
    * [X] **_sendMessageToUser_**
      * Given a message **subject**, **body**, **recipient**, and **sender**, create a new message.  
    * [X] **_sendMessageToGroup_**
      * This should operate similarly to **sendMessageToUser** only it should send the message to *every* member currently in the specified group.  
    * [X] **_displayMessages_**
      * Given a **user**, look up all of the messages sent to that user (either directly or via a group that they belong to). The program should print out the user's messages in a nicely formatted way.  
    * [X] **_displayNewMessages_**
      * Operates similarly to **displayMessages**, but only displays messages sent since the user's last login.  
    * [X] **_searchForUser_**
      * This provides a simple search function for the system. Given a string on which to match any user in the system, any item in this string must be matched against any significant field of a user's profile. That is if the user searches for "**xyz abc**", the results should be the set of all profiles that match "**xyz**" UNION the set of all profiles that matches "**abc**". The names of all matching users should be printed out in a nicely formatted way.  
    * [X] **_threeDegrees_**
      * This task explores the user's social network. Given two users (**userA** and **userB**), find a path, if one exists, between the **userA** and the **userB** with at most 3 hop between them. A hop is defined as a friendship between any two users. The path should be printed out in a nicely formatted way.  
    * [X] **_topMessages_**
      * Display the top **K** who have sent or received the highest number of messages during for the past **X** months. **X** and **K** should be input parameters to this function.  
    * [X] **_dropUser_**
      * Remove a user and all of their information from the system. When a user is removed, the system should then delete the user from the groups he or she was a member of using a trigger. Note that messages require special handling because they are owned by both the sender and the receiver. Therefore, a message is deleted only when both the sender and all receivers are deleted. Attention should be paid handling integrity constraints.  
2. [X] Create a commit labeled "**Milestone 2 submission**" and push it to the repository.  
3. [X] Send an email to nlf4@pitt.edu and weg21@pitt.edu:  
  * [X] The email title should be "**[CS1555] Project milestone 2 submission**".  
  * [X] Insert a link to the repository.  
  * [X] Insert the GitHub commit ID (40-character string / SHA-1 hash).  

### Milestone 3: Bringing It All Together (Due: August 4 @ 11:59PM)

1. [X] Create a Java driver program to demonstrate the FaceSpace database by calling all of the created methods from Milestone 2.
  * [ ] Have an "interactive" mode where the user can call each function individually and input custom data.
  * [ ] Have an "automatic" mode that will perform standard and edge case testing on all of the methods.
3. [ ] Create a commit labeled "**Milestone 3 submission**" and push it to the repository.  
4. [ ] Send an email to nlf4@pitt.edu and weg21@pitt.edu:  
  * [ ] The email title should be "**[CS1555] Project milestone 3 submission**".  
  * [ ] Insert a link to the repository.  
  * [ ] Insert the GitHub commit ID (40-character string / SHA-1 hash).  
