# Hibernate CRUD Entity Test

This project tests [Hibernate's](https://hibernate.org/)
abstract functionality to *store*, *retrieve*, *edit* and *delete* data 
from an embedded [H2](https://www.h2database.com/html/main.html)
database engine.

## Getting Started

All the dependencies needed to clone and test this project are
supplied into the **pom.xml** file in the project directory. But
that means we should have [Maven](https://maven.apache.org/) installed
on our local machine, so that project can be built and tested.

### Prerequisites

This project is written and compiled in **JDK 12**. Either Oracle's
or OpenJDK should be installed on local machine.

**Maven:**

Follow [apache's instructions](https://maven.apache.org/install.html)
to set it up.

### Installing

To clone this repository:

```shell
git clone https://github.com/pagidas/maven-hibernate-h2-test.git
```

On the root project directory, where **pom.xml** is located:

```shell
mvn compile
```

Downloads all the dependencies, jar files that are needed
for the project.

```shelll
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  5.122 s
```

This will be shown on the terminal screen if all succeeded.

## Running the Tests

To run the test we simply run the maven's **phase** which is
integrated with the **JUnit** framework to execute the tests.

```shell
mvn test
```

The way I wrote my test, before any test that would use **SessionFactory**
instance to open a **Session** to store-retrieve data from the database,
I wrote a **@BeforeClass** method to get (or initialize) a **SessionFactory**
instance.

```java
public class TestHibernateStudentCRUD {

    private static SessionFactory factory;
    private static Session session;

    @BeforeClass
    public static void setupTest() {
        // gets the current SessionFactory (or creates if null)
        factory = HibernateUtil.getSessionFactory();
    }
    
    /* ... */
}
```

Have a look at **HibernateUtil** in my *hibernate.util* package
in the src folder to understand how Hibernate functionality
is bootstrapped so that can be easily started and closed.

When we:

```java
public class TestHibernateStudentCRUD {
    
    /* ... */
    
    @Test
    public void TestCreateUser() {
        
        /* mandatory steps before saving an entity */
        
        session.save(new Student("test-firstname", "test-lastname"));
    }
}
```

Hibernate parses the annotations written in the POJO Entity class and
matches the table columns with the class fields.

Using:

```properties
hibernate.hbm2ddl.auto=create-drop
```

in the hibernate.properties file will tell Hibernate to CREATE
the table if it does not exist, and DROP it too, so that no data
is stored forever in the database.

The output of the maven's phase _**test**_ should be:

```shell
Creating a new Student...
Saving the Student...
Hibernate: insert into student (id, first_name, last_name) values (null, ?, ?)
Done!

Looking for that Student...
Done!

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.832 sec

Results :

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  5.122 s
```
