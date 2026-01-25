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

### Prerequisites
* Java Development Kit (JDK) 15 or higher (Uses Text Blocks).

### Navigate to the folder:
cd taskmanagerEncryption
### Compile the source files:
javac main.java tasks.java
### Run the application
java main

## Showcase
### Opening application
<img width="1097" height="446" alt="image" src="https://github.com/user-attachments/assets/17a1322b-2960-4ff4-93a0-5858a6d2a033" />

### Printing tasks

<img width="977" height="478" alt="image" src="https://github.com/user-attachments/assets/d513597d-e0a8-4c2b-9876-98ca79b330f7" />

### Adding task

<img width="1105" height="726" alt="image" src="https://github.com/user-attachments/assets/780e1e03-d020-49de-b2ca-59162af83ebb" />

### Invalid inputs

<img width="1345" height="862" alt="image" src="https://github.com/user-attachments/assets/bf47e2fd-7597-42b5-bb3d-98772ff0e09c" />

### Save and Exit application

<img width="983" height="287" alt="image" src="https://github.com/user-attachments/assets/cf5430f7-d2a8-4650-be28-c87e3aafc46a" />

### ### Example of txt file with encrypted tasks

<img width="776" height="945" alt="image" src="https://github.com/user-attachments/assets/b05c2695-2e8b-4b1b-bc8f-65be24192da5" />








