<h1>Final project in Software Architecture and Framworks Spring 2019</h1>
<p>Subject link: https://student.oslomet.no/en/studier/-/studieinfo/emne/DAVE3615/2018/H%C3%98ST 
<br>
</p>
<h2> Assignment details </h2>
<p>
I chose to make a Reddit clone(One of the choices), and here are
the feautures of the project.

<h3> Technologies used: </h3>
<p>h2 database</p>
<p>Spring boot</p>
<p>Spring Security</p>
<p>Lombok</p>
<p>Thymeleaf</p>
<p>Bootstrap css templates</p>
<p>Docker</p>
<p>Deployed on DigitalOcean</p>

<h2>Brief explanation of each of the microservices</h2>
<h4>UserService</h4>
<p>Database for User(Authenticaton)</p>
<p><b>Description</b></p>
<ul>
<li><p>Ths service is responsible for taking the user information and storing it in the database.
Then it will map the data to JSON and share it to the AuthenticationAndFrontEndService for login and some id verification.</p></li>
<li><p>When this service is called  it passes the updated/created users id to the
ForumService which creates a User copy object which has the same id. This is to ensure that each service corresponing ids in both services to keep posts linked to the corrct User.</p></li>

<li><p>Has an own database to make it more independent.</p></li>


</ul>
<p><b>Dependencies</b></p>
<ul>

<li><p>Self sustained. is called upon creation, and validation of users.</p></li>
</ul>

<p><b>API Endpoints</b></p>
<ul>

<li>
	<p>Endpoint: /users</p>
	<ul>
		<li>HTTP Method: Get</li>
		<li>Parameters: </li>
		<li>Return type: List<User><li>
		<li>Description: Returns all users.</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /users/{id}</p>
	<ul>
		<li>HTTP Method: Get</li>
		<li>Parameters: long id</li>
		<li>Return type: User</li>
		<li>Description: Finds a user by id, and returns the User object.</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /users/findEmail/{email}</p>
	<ul>
		<li>HTTP Method: Get</li>
		<li>Parameters: String email</li>
		<li>Return type: User</li>
		<li>Description: Finds a user by email. If it exists return the User Object</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /users/{id}</p>
	<ul>
		<li>HTTP Method: Delete</li>
		<li>Parameters: long id </li>
		<li>Return type: void</li>
		<li>Description: Delete the user given by id. Will also send a request to ForumService to delete the user in that DB too.</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /users</p>
	<ul>
		<li>HTTP Method: Post	</li>
		<li>Parameters: User newUser </li>
		<li>Return type: void</li>
		<li>Description: Saves the new user to the database.</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /users/{id}</p>
	<ul>
		<li>HTTP Method: Put</li>
		<li>Parameters: long id, User newUser</li>
		<li>Return type: void</li>
		<li>Description: Updates the User with the given ID with the body of the new User.</li>
	
	</ul>

</li>




</ul>

<h4>ForumService</h4>

<p>Connects to database for posts, channels and forumuser. </p>
<p><b>Description</b></p>

<ul>
<li><p>This service manages the mapping of posts, channels and users and stores it to a seperate
database than the one for UserService. The data is later mapped to the JSON and html
templates. This Service gets partially updated by the UserService whenever an User
object is edited. It communicates over rest api to send channels, forums, and user(ForumUser).</p></li>

</ul>
<p><b>Dependencies</b></p>
<ul>

<li><p>Dependent on ForumUser Objects being sent from UserService</p></li>
<li><p>Need data created in AuthenticationAndFrontEndService to have data to store.</p></li>
</ul>

<p><b>API Endpoints</b></p>
<ul>

<li>
	<p>Endpoint: /updateUserDataBase</p>
	<ul>
		<li>HTTP Method: Get</li>
		<li>Parameters: </li>
		<li>Return type: void</li>
		<li>Description: Gets all users from the UserService database and updates the forumservice DB with the needed information. Discards the rest.</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /users</p>
	<ul>
		<li>HTTP Method: Get</li>
		<li>Parameters: </li>
		<li>Return type: List<User></li>
		<li>Description: Returns a list of all the users in the database.</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /users/{id}</p>
	<ul>
		<li>HTTP Method: Get</li>
		<li>Parameters: long id</li>
		<li>Return type: User</li>
		<li>Description: Gets a User By Id, and returns the full User object.</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /users/{id}</p>
	<ul>
		<li>HTTP Method: Put</li>
		<li>Parameters: long id, User newUser</li>
		<li>Return type: void</li>
		<li>Description: Finds a User by ID, and saves the new info on top of the User. The User keeps its references to channels, posts and followers.</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /users/{id}</p>
	<ul>
		<li>HTTP Method: Delete</li>
		<li>Parameters: long id </li>
		<li>Return type: void</li>
		<li>Description: Delets the User by id.</li>
	
	</ul>

</li>
<li>
	<p>Endpoint: /users</p>
	<ul>
		<li>HTTP Method: Post</li>
		<li>Parameters: User user</li>
		<li>Return type: void</li>
		<li>Description: Saves a new User to the database.</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /users/followUser/{id}</p>
	<ul>
		<li>HTTP Method: Post</li>
		<li>Parameters: long id, User user</li>
		<li>Return type: void</li>
		<li>Description: Makes the User provided by ID follow the User object.</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /users/subto/{channel}</p>
	<ul>
		<li>HTTP Method: Post</li>
		<li>Parameters: String channel, User user</li>
		<li>Return type: void</li>
		<li>Description: Makes the provided user subscribe to the channel..</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /posts</p>
	<ul>
		<li>HTTP Method: Get</li>
		<li>Parameters: </li>
		<li>Return type: List<Post></li>
		<li>Description: Returns a list of all the posts in the database.</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /posts</p>
	<ul>
		<li>HTTP Method: Post</li>
		<li>Parameters: Post newPost</li>
		<li>Return type: void</li>
		<li>Description: Saves a new Post to the database.</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /posts/{id}</p>
	<ul>
		<li>HTTP Method: Get</li>
		<li>Parameters: long id </li>
		<li>Return type: Post</li>
		<li>Description: Gets a post By Id, and returns the full Post object.</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /posts/likePost/{id}</p>
	<ul>
		<li>HTTP Method: Post</li>
		<li>Parameters: Post post, long id</li>
		<li>Return type: void</li>
		<li>Description: Makes the provided Users id subscrine to the Post.</li>
	
	</ul>

</li>
<li>
	<p>Endpoint: /posts/fromchan/{id}</p>
	<ul>
		<li>HTTP Method: Get</li>
		<li>Parameters: String id</li>
		<li>Return type: List<Post></li>
		<li>Description: Gets the posts in one channel(category).</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /posts/postsByUser/{id}</p>
	<ul>
		<li>HTTP Method: Get</li>
		<li>Parameters: long id</li>
		<li>Return type: List<Post></li>
		<li>Description: Returns all the posts made by a user..</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /channels</p>
	<ul>
		<li>HTTP Method: Get</li>
		<li>Parameters: </li>
		<li>Return type: List<Channel></li>
		<li>Description: Returns a list of all the posts in the database.</li>
	
	</ul>

</li>


<li>
	<p>Endpoint: /channels/{id}</p>
	<ul>
		<li>HTTP Method: Get</li>
		<li>Parameters: long id </li>
		<li>Return type: Channel</li>
		<li>Description: Gets a post by Id, and returns the full Channel Object.</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /channels</p>
	<ul>
		<li>HTTP Method: Post</li>
		<li>Parameters: Channel newChannel</li>
		<li>Return type: void</li>
		<li>Description: Saves a new Post to the database.</li>
	
	</ul>

</li>

<li>
	<p>Endpoint: /channels/chaninuser/{id}</p>
	<ul>
		<li>HTTP Method: Get</li>
		<li>Parameters: long id</li>
		<li>Return type: List<Channel></li>
		<li>Description: Get all the channels an User is subscribed to..</li>
	
	</ul>

</li>




</ul>

<h4>AuthenticationAndFrontEndService</h4>
<p><b>Description</b></p>
<ul>
<li><p>Authenticates the session with the UserService, and gets posts and channels from
ForumService. It seperates User classes from both other services with ForumUser(for the
forumService’s user) and User for the authenticaiton one. The User is only used when
authenticaiton is required.</p></li>
<li><p></p></li>

<li><p></p></li>


</ul>
<p><b>Dependencies</b></p>
<ul>

<li><p>Need to fetch authentication data from UserService</p></li>
<li><p>Needs to fetch post,channel, forumUser data from ForumService</p></li>
</ul>

<p><b>Functionality:</b></p>
<ul>

	<li> Create Account.</li>
	<li> Login, Logout.</li>
	<li> Session Management.</li>
	<li> Microservices communication using REST APIs.</li>
	<li> 3 microservices in the project.</li>
	<li>Main Page showing all posts(login not necesarry).</li>
	<li> User can see two pages: 
	<ul>
		<li> All posts: Posts from all users appear here.</li>
		<li> Channel posts: Posts from subscribed channels appear here.</li>
	</ul>
	</li>
	<li> A channel is like a forum topic(example: bitcoin, arts). Each posts should belong to a channel.</li>
	<li> User can search for a post or a channel.</li>
	<li>User can Create a post with either free text or image.</li>
	<li>The post should be posted in a channel.</li>
	<li>User can subscribe to a channel and will only see the popsts from that channel.</li>
	<li>The user can like a post by other users.</li>
	<li>User can follow other users and thus see their posts on their page.</li>
	<li>User can see their friend list(The users they follow)</li>
	<li>User porfile page where user can change password, delete account etc.</li>
	<li> Admin functionality:
	<ul>
		<li>Admin can look at all users in the system.</li>
		<li>Admin can delete a user or update their password.</li>
	</ul>   
	</li>
	</li>
	<li>Initial data dump: 10 posts, 5 channels and 4 different users. Login credentials for testing in src/main/java/no/oslomet/authenticationfrontendmicroservice/controller/MainController.java</li>

</ul>
