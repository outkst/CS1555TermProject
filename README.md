# CS1555TermProject

**Term Project for Pitt CS1555 Database Management Systems (Summer 2016).**

[CS1555 Website](https://people.cs.pitt.edu/~nlf4/cs1555/)  *|* [Recitation Website](https://people.cs.pitt.edu/~weg21/1555.html)

A full list of this project's requirements can be viewed in **_1555_term_project.pdf_**

#### Milestone 0: Assemble Teams (Due: July 6 @ 11:59PM)

1. [X] Add *nfarnan* to the contributors for the repository.  
2. [X] Add *weg21* to the contributors for the repository.  
3. [X] Add all team members (name, pitt username, github username) to *team_members.txt*.  
4. [X] Create a commit labeled "Milestone 0 submission" and push it to the repository.  
5. [X] Send an email to nlf4@pitt.edu and weg21@pitt.edu:  
  * [X] The email title should be "[CS1555] Project milestone 0 submission"  
  * [X] Insert a link to the repository.  
  * [X] Insert the GitHub commit ID (40-character string / SHA-1 hash)  

#### Milestone 1: The FaceSpace Database Schema & Example Data (Due: July 11 @ 11:59PM)
1. [ ] Database Schema Overview:
  * [ ] Choose appropriate data types to make up the attributes of each relation.
  * [ ] Define all structural and semantic integrity constraints.
  * [ ] All assumptions must be stated using comments within the database creation script.
  * [ ] Messages will be constrained to be less than 100 characters.
2. [ ] Proposed Database Schema (*In Progress...*)   
  * [ ] **Users**
    * [ ] **ID** *number(10)*
    * [ ] **Name** *varchar2(100)*
    * [ ] **Email** *varchar2(80)*
    * [ ] **DOB** *date*
    * [ ] **LastLogin** *timestamp*
  * [ ] **Friendships**
    * [ ] **UserID** *number(10)*
    * [ ] **FriendID** *number(10)*
    * [ ] **Approved** *number(1)*
    * [ ] **DateApproved** *timestamp*
  * [ ] **Groups**
    * [ ] **ID** *number(10)*
    * [ ] **Name** *varchar2(100)*
    * [ ] **Description** *varchar2(500)*
    * [ ] **Limit** *number(10)*
    * [ ] **DateCreated** *timestamp*
  * [ ] **GroupMembers**
    * [ ] **GroupID** *number(10)*
    * [ ] **UserID** *number(10)*
    * [ ] **DateJoined** *timestamp*
  * [ ] **Messages**
    * [ ] **ID** *number(20)*
    * [ ] **SenderID** *number(10)*
    * [ ] **Subject** *varchar2(30)*
    * [ ] **Body** *varchar2(100)*
    * [ ] **RecipientID** *number(10)*
    * [ ] **DateCreated** *timestamp*
3. [ ] EXAMPLE DATA
  * [ ] 100+ Users
  * [ ] 200+ Friendships
  * [ ] 10+ Groups
  * [ ] 300+ Messages
