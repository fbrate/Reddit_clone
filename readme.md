l project in Software Architecture and Framworks Spring 2019</h1>
Subject link: https://student.oslomet.no/en/studier/-/studieinfo/emne/DAVE3615/2018/H%C3%98ST 
<br>

<h2> Assignment details </h2>

I chose to make a Reddit clone(One of the choices), and here are
the feautures of the project.

<h3> Technologies used: </h3>
h2 database
Spring boot
Spring Security
Lombok
Thymeleaf
Bootstrap css templates
Docker
Deployed on DigitalOcean

<h2>Brief explanation of each of the microservices</h2>
<h4>UserService</h4>
Database for User(Authenticaton)


**Description**

* Ths service is responsible for taking the user information and storing it in the database.
Then it will map the data to JSON and share it to the AuthenticationAndFrontEndService for login and some id verification.
* When this service is called  it passes the updated/created users id to the
ForumService which creates a User copy object which has the same id. This is to ensure that each service corresponing ids in both services to keep posts linked to the corrct User.

* Has an own database to make it more independent.



**Dependencies**


* Self sustained. is called upon creation, and validation of users.


**API Endpoints**



* Endpoint /users

    * HTTP Method: Get
  * Parameters: 
  * Return type: List<User>* 
  * Description: Returns all users.






* Endpoint /users/{id}

  * HTTP Method: Get
  * Parameters: long id
  * Return type: User
  * Description: Finds a user by id, and returns the User object.






* Endpoint /users/findEmail/{email}

  * HTTP Method: Get
  * Parameters: String email
  * Return type: User
  * Description: Finds a user by email. If it exists return the User Object






* Endpoint /users/{id}

  * HTTP Method: Delete
  * Parameters: long id 
  * Return type: void
  * Description: Delete the user given by id. Will also send a request to ForumService to delete the user in that DB too.






* Endpoint /users

  * HTTP Method: Post	
  * Parameters: User newUser 
  * Return type: void
  * Description: Saves the new user to the database.






* Endpoint /users/{id}

  * HTTP Method: Put
  * Parameters: long id, User newUser
  * Return type: void
  * Description: Updates the User with the given ID with the body of the new User.










<h4>ForumService</h4>

Connects to database for posts, channels and forumuser. 


**Description**


* This service manages the mapping of posts, channels and users and stores it to a seperate
database than the one for UserService. The data is later mapped to the JSON and html
templates. This Service gets partially updated by the UserService whenever an User
object is edited. It communicates over rest api to send channels, forums, and user(ForumUser).


**Dependencies**


* Dependent on ForumUser Objects being sent from UserService
* Need data created in AuthenticationAndFrontEndService to have data to store.


**API Endpoints**



* Endpoint /updateUserDataBase

  * HTTP Method: Get
  * Parameters: 
  * Return type: void
  * Description: Gets all users from the UserService database and updates the forumservice DB with the needed information. Discards the rest.






* Endpoint /users

  * HTTP Method: Get
  * Parameters: 
  * Return type: List<User>
  * Description: Returns a list of all the users in the database.






* Endpoint /users/{id}

  * HTTP Method: Get
  * Parameters: long id
  * Return type: User
  * Description: Gets a User By Id, and returns the full User object.






* Endpoint /users/{id}

  * HTTP Method: Put
  * Parameters: long id, User newUser
  * Return type: void
  * Description: Finds a User by ID, and saves the new info on top of the User. The User keeps its references to channels, posts and followers.






* Endpoint /users/{id}

  * HTTP Method: Delete
  * Parameters: long id 
  * Return type: void
  * Description: Delets the User by id.




* Endpoint /users

  * HTTP Method: Post
  * Parameters: User user
  * Return type: void
  * Description: Saves a new User to the database.






* Endpoint /users/followUser/{id}

  * HTTP Method: Post
  * Parameters: long id, User user
  * Return type: void
  * Description: Makes the User provided by ID follow the User object.






* Endpoint /users/subto/{channel}

  * HTTP Method: Post
  * Parameters: String channel, User user
  * Return type: void
  * Description: Makes the provided user subscribe to the channel..






* Endpoint /posts

  * HTTP Method: Get
  * Parameters: 
  * Return type: List<Post>
  * Description: Returns a list of all the posts in the database.






* Endpoint /posts

  * HTTP Method: Post
  * Parameters: Post newPost
  * Return type: void
  * Description: Saves a new Post to the database.






* Endpoint /posts/{id}

  * HTTP Method: Get
  * Parameters: long id 
  * Return type: Post
  * Description: Gets a post By Id, and returns the full Post object.






* Endpoint /posts/likePost/{id}

  * HTTP Method: Post
  * Parameters: Post post, long id
  * Return type: void
  * Description: Makes the provided Users id subscrine to the Post.





* Endpoint /posts/fromchan/{id}

  * HTTP Method: Get
  * Parameters: String id
  * Return type: List<Post>
  * Description: Gets the posts in one channel(category).






* Endpoint /posts/postsByUser/{id}

  * HTTP Method: Get
  * Parameters: long id
  * Return type: List<Post>
  * Description: Returns all the posts made by a user..






* Endpoint /channels

  * HTTP Method: Get
  * Parameters: 
  * Return type: List<Channel>
  * Description: Returns a list of all the posts in the database.







* Endpoint /channels/{id}

  * HTTP Method: Get
  * Parameters: long id 
  * Return type: Channel
  * Description: Gets a post by Id, and returns the full Channel Object.






* Endpoint /channels

  * HTTP Method: Post
  * Parameters: Channel newChannel
  * Return type: void
  * Description: Saves a new Post to the database.






* Endpoint /channels/chaninuser/{id}

  * HTTP Method: Get
  * Parameters: long id
  * Return type: List<Channel>
  * Description: Get all the channels an User is subscribed to..










<h4>AuthenticationAndFrontEndService</h4>


**Description**

* Authenticates the session with the UserService, and gets posts and channels from
ForumService. It seperates User classes from both other services with ForumUser(for the
	forumService’s user) and User for the authenticaiton one. The User is only used when
	authenticaiton is required.

**Dependencies**
	
	
* Need to fetch authentication data from UserService
* Needs to fetch post,channel, forumUser data from ForumService
	
	
**Functionality:**
	
*  Create Account.
*  Login, Logout.
*  Session Management.
*  Microservices communication using REST APIs.
* Main Page showing all posts(login not necesarry).
*  User can see two pages: 
  *  All posts: Posts from all users appear here.
  *  Channel posts: Posts from subscribed channels appear here.
*  A channel is like a forum topic(example: bitcoin, arts). Each posts should belong to a channel.
*  User can search for a post or a channel.
* User can Create a post with either free text or image.
* The posts are posted in channels.
* User can subscribe to a channel and can then see only subscribed posts in a section.
* The user can like a post by other users.
* User can follow other users and thus see their posts on their page.
* User can see their friend list(The users they follow)
* User profile page where user can change password, delete account etc.
*  Admin functionality:
  * Admin can look at all users in the system.
  * Admin can delete a user or update their password.	
* Initial data dump: 10 posts, 5 channels and 4 different users. Login credentials for testing in src/main/java/no/oslomet/authenticationfrontendmicroservice/controller/MainController.java
	
	

