
# Film Query
## Week 7 Homework for SkillDistillery
### Overview
This project is pretty simple. Use an SQL database paired with java to build objects in a database that are well modeled. The specific program does so from a database of fictional movies and actors.

It gives the user a menu and allows them to look up movies by keyword which gives them a short description(including an id) and if a user has an id they can look at any specific movie. 
### Concepts
- Object Oriented Code
- Static Serialization
- Abstraction
- PolyMorphism
- InterFaces
- Inheritance
- Encapsulation
- SQL

### Technologies Used
- Eclipse
- Java
- SQL
- Git
- OO

### Lessoned Learned
I tried quite hard to get the try with resources to work and i finally did. My solution seems like it works. essentially I just create an inner class that implements autoclosable. This class compiles and runs the query that it takes in as an input to its method. It also handles most of the closing so that accessing a database with a sql prepared statement and a list of arguments is easy.

I also slightly adjusted the single movie output display to make it more readable.
