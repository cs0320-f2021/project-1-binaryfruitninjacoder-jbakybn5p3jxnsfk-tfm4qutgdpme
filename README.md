# README

## Recommender using KNN and Bloom Filter

### ✏️ Description

The Project uses a simple REPL to generate the k nearest neighbors of a person based on their similarity and differences to other people based on a set of both numerical and categorical attributes.
The data are retrieved from a user API and SQL user data, which are combined to create a table of members. The user inputs a person to check best k matches, and the recommender algorithm returns the top k recomendations.

### ⚙️ Design

**Overall Architecture**
Queries are passed into the system through command line, which are passed into our FileReader. The set of commands are stored 
in a Hashmap mapping command string to a handler that handles te command and provide desired output. When user load the data of members,
these information are processed through LoadHandler and stored in a global variable called AllMembers static class. Later on by calling getAllMembers method,
one could get all members, which is in the form of a map mapping student id to a Member object representing the student. 
RecommendHandler is used to return a list of top k recommended members based on the queried student. GenHandler produces a list of recommended group mates
based on the user queried. API and SQL data are loaded into the AllMembers through LoadHandler.

**KD Tree Component**
The KD Tree mainly takes the use of generics to generalize the application to multi-parameters nodes and multi-dimensional space when calculating the euclidean distance. In our implementation, the KD Tree is a class, INode an interface of a tree node in the KD Tree, and ThreeDimNode class as a class for nodes that have 3 dimensions. An alternative implementation approach is to create a generic interface for the KD Tree itself as well, which we did not have the time to implement just yet. Stay tuned for a more generic implementation in the coming days. Each node has a set of methods that could help find the euclidean distance between this node and another node. KD Tree has methods that construct a balanced tree taking the use of the quick select algorithm, which is called directly when parameters such as a list of nodes and the dimension are passed into the KD Tree constructor. It has methods `contains` that checks if a certain node is in the tree, and most importantly, it has the method KNNSearch looking for the K nearest Neighbors of a specific node.

**ORM component**
The ORM component reads sqlite files and turns all rows of data into Objects. The API component allows the same thing. Data collected using the ORM method gives us information about a user's interests, positive, negative, and skills, whereas the API method gives us information about their name, grade, years of experience, meeting time, preferred language etc. Inside a LoadHandler class, there is a for loop that goes through k users using the API data and then adds that information into k objects of the Member class. Similarly, all four ORM data classes will be looped through and have their info accessed by getters to add into the Member class. This is how we would approach it, but it isn’t the most efficient way to approach it. Then, we will add all the Members into the AllMembers class.

**Generic REPL**
Our REPL class is a hashmap object where Strings are keys and handler methods are values. The REPL class is static and contains one run method where a user input is a parameter that returns type void. The run method iterates through a hashmap determining if the command is recognized and calling the value methods if the command is present in the key set. The method called is a common method (handle) that is present in command handler classes which all implement a handler interface. Each command handler class is responsible for either loading data from api/orm sources or for making recommendations in specific output formats. The command handler classes all implement a handler interface that contains the same method handle where a string is a parameter and return type. The main class of the project instatites a REPL, puts known commands and methods into the hashmap, and calls the run REPL method.

**Recommender Algorithm**
Params to take: id, name, meeting, years_of_experience, interests, commenting, testing, OOP, algorithms, frontend

Bloom filter (find similarity): meeting, interests

KNN: years of experience, commenting (reverse user's), testing (reverse user's), OOP (reverse user's), algorithms (reverse user's), frontend (reverse user's)

Assume we want to find top k users that matches, then first use KD Tree to pick top 4k KNN ( in the top 4k nearest neighbors, 2k are "most similar" to the queried user, and the other 2k are the "most dissimilar" to the user) then from the top 4K KNN, pick top k using Bloom filter on the two attributes mentioned above.
Implementation of the algorithm please refer to RecommendHandler class. Note: The reverse top 2kNN is currently not in place in the code, but given more time it should be implemented.

#### Space & Time Analysis

KD Tree Component: The time complexity of KD Tree construction takes O(logn) time thanks to the quick select algorithm. The time complexity of the KD tree `contains` method is O(logn), and the time complexity of KNNSearch is also O(logn) thanks to carving the space to find neighbors.

### 🐛 Errors & Bugs

The main errors of the program are a result of incorrect user INPUT. The program will log specific error message when
the user input an invalid file path, the csv format is wrong, doesn't follow the correct query format, or query a
non-existent person.


### ✨ Contribution
 - Yueshan: KD Tree, RecommendHandler, Documentation, project overview, integration for Bloom filter and KNNSearch algorithm with RecommendHandler, Member, Teammate, and other intermediate DS to store object representation of a student.
 - Ivery: I implemented the ORM component, read through to understand the API component, wrote classes for the ORM tables, and implemented the LoadHandler class. If I had more time, I would work on figuring out the best way to integrate the two different pieces of data from API and ORM that loads all the attributes neatly into the Members class.
 - Grace: I implemented the generic REPL class which involved reading and using my partners’ api and orm code to write handler classes that interacted with their data structures and algorithms. Specifically I wrote handler classes for classify, recommend, command, and generate. If I had more time I would’ve liked to spend more time working on generate. I used a double for loop which isn’t very time efficient.
