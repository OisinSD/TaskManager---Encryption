# Encrypted Task Manager (Terminal-Based) 

A lightweight, Java-based terminal application designed to manage your daily activities with a focus on privacy. This application stores your tasks in a local `.txt` file, but uses encryption to ensure your data remains unreadable to anyone opening the file directly.

##  Features
* **Encrypted Storage:** All tasks are encrypted after saving using "save" before being written to `Task.txt`.
* **Decryption:** Tasks are decrypted when the password is correct and the Tasks.txt file is scanned.
* **Terminal Interface:** A clean, command-line interface.
* **Persistent Data:** Tasks are saved locally and persist between sessions.

##  Commands
* `add`: Create a new task.
* `print`: Display your current task list.
* `remove`: Delete a specific task from your list.
* `save`: Writes encrypted tasks to Tasks.txt.
* `help`: View the command menu.
* `exit`: Ends the session.

## Encryption 
I decided to encrypt and decrypt myself using XOR on the bytes of the task and private key.
This helped me better understand how encryption worked. 

##  Getting Started

### Navigate to the folder:
cd taskmanagerEncryption
### Compile the source files:
javac main.java tasks.java
### Run the application
java main

## Showcase
### Opening application

<img width="1912" height="980" alt="image" src="https://github.com/user-attachments/assets/9f3ca8f2-ec13-4138-a098-d2d11562add3" />

### Logging in 

<img width="1911" height="981" alt="image" src="https://github.com/user-attachments/assets/ac33b60c-36c5-4b92-9772-71daf3f9292b" />

### Printing tasks

<img width="1915" height="981" alt="image" src="https://github.com/user-attachments/assets/fae0e2f4-6214-4da4-ab53-6dbbfb3cbcb1" />
<img width="1917" height="977" alt="image" src="https://github.com/user-attachments/assets/6ad27361-c27e-4dbe-8b92-2ba37456bb60" />

### Adding task

<img width="1917" height="978" alt="image" src="https://github.com/user-attachments/assets/c8f3d42e-5571-4818-86ac-6e0888db9d5e" />

### Invalid inputs

<img width="1915" height="975" alt="image" src="https://github.com/user-attachments/assets/2f80eaab-7531-4f83-86c1-3f47d8352b67" />

### Help command

<img width="1917" height="978" alt="image" src="https://github.com/user-attachments/assets/5c37222f-8246-4396-b799-758e828b3def" />

### Save and Exit application

<img width="1911" height="972" alt="image" src="https://github.com/user-attachments/assets/aefed5fa-f1a6-4746-9aee-fa07d94993ec" />

### ### Example of txt file with encrypted tasks

<img width="1915" height="1020" alt="image" src="https://github.com/user-attachments/assets/4d34e417-a857-4ec2-a360-9d42a523b1f4" />


## Engineering Retrospective & Learning Journey

This project was developed as a fundamental learning exercise to explore the "first principles" of security, persistence, and data integrity in Java. While the system is functional, I have documented the following design choices and security trade-offs to demonstrate my understanding of production-grade standards.

### Security & Cryptography Analysis

#### 1. Hashing & Identity (SHA-256)
The current implementation uses a static SHA-256 hash to verify the password. 
* **Self-Correction:** I recognise that "Plain Hashing" is vulnerable to **Rainbow Table attacks**. 
* **Industry Standard:** In a production environment, I would have to implement **Salting** (adding random data to the input) and use a key-stretching algorithm like **Argon2** or **BCrypt** to increase the computational cost of brute-force attacks.

#### 2. Data Encryption (XOR Implementation)
For file persistence, I implemented a custom XOR cypher. 
* **Why XOR?** I chose to "roll my own" encryption specifically to understand bitwise operations and the underlying mechanics of how data is transformed at the byte level.
* **Security Insight:** I am aware of the industry rule: *"Never roll your own crypto."* XOR encryption is susceptible to frequency analysis and "Known-Plaintext" attacks. 
* **Path Forward:** For the next iteration, I plan to migrate to **AES-256 (Advanced Encryption Standard)** using the Java Cryptography Architecture (JCA) to ensure industry-standard security.

#### 3. Code Quality 
I identified key areas for refactoring to meet production standards:
* Control Flow (Recursion vs Iteration): The current method "addTask()" calls itself. This risks a StackOverflow as the stack frames accumulate (if a user were to add many tasks in one sitting).
* Correction: I would use a simple while loop to handle the navigation without growing the stack

### Future Roadmap
To move this project from a local tool to a scalable system, I have identified the following goals:
* **Dependency Management:** Transition from raw `.java` files to a **Maven** project structure.
* **Persistence:** Move from `.txt` flat-file storage to **JSON/Binary serialisation** or an embedded database like H2.
* **Testing:** Implement **JUnit 5** suites to ensure 100% coverage of the encryption and hashing logic.









