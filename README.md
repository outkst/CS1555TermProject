# CS1555TermProject

**Term Project for Pitt CS1555 Database Management Systems (Summer 2016).**

[CS1555 Website](https://people.cs.pitt.edu/~nlf4/cs1555/)  *|* [Recitation Website](https://people.cs.pitt.edu/~weg21/1555.html)

A full list of this project's requirements can be viewed in **_1555_term_project.pdf_**

#### Milestone 0: Assemble Teams (Due: July 6 @ 11:59PM)

1. [X] Add *nfarnan* to the contributors for the repository.  
2. [X] Add *weg21* to the contributors for the repository.  
3. [X] Add all team members (name, pitt username, github username) to **_team_members.txt_**.  
4. [X] Create a commit labeled "Milestone 0 submission" and push it to the repository.  
5. [X] Send an email to nlf4@pitt.edu and weg21@pitt.edu:  
  * [X] The email title should be "[CS1555] Project milestone 0 submission"  
  * [X] Insert a link to the repository.  
  * [X] Insert the GitHub commit ID (40-character string / SHA-1 hash)  

#### Milestone 1: The FaceSpace Database Schema & Example Data (Due: July 11 @ 11:59PM)

1. [ ] Database Schema Overview:  
  * [X] Choose appropriate data types to make up the attributes of each relation.  
  * [ ] Define all structural and semantic integrity constraints.  
  * [ ] All assumptions must be stated using comments within the database creation script.  
  * [X] Messages will be constrained to be less than 100 characters.  
2. [ ] Proposed Database Schema (*In Progress...*)  
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
5. [ ] Send an email to nlf4@pitt.edu and weg21@pitt.edu:  
  * [ ] The email title should be "[CS1555] Project milestone 1 submission"  
  * [ ] Insert a link to the repository.  
  * [ ] Insert the GitHub commit ID (40-character string / SHA-1 hash)  
