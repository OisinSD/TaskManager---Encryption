# Encrypted Task Manager (Terminal-Based) 

A lightweight, Java-based terminal application designed to manage your daily activities with a focus on privacy. This application stores your tasks in a local `.txt` file, but uses encryption to ensure your data remains unreadable to anyone opening the file directly.

##  Features
* **Encrypted Storage:** All tasks are encrypted before being written to `Task.txt`.
* **On-the-Fly Decryption:** Tasks are decrypted only when viewed within the application.
* **Terminal Interface:** A clean, command-line interface.
* **Persistent Data:** Tasks are saved locally and persist between sessions.

##  Commands
* `add`: Create a new task (automatically encrypted).
* `print`: Decrypt and display your current task list.
* `remove`: Delete a task from your list.
* `help`: View the command menu.
* `exit`: Ends the session.

##  Getting Started

### Prerequisites
* Java Development Kit (JDK) 15 or higher (Uses Text Blocks).


