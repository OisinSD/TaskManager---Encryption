package taskmanagerEncryption;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;


public class main{

    public static final String RED = "\u001B[31m";
	public static final String RESET = "\u001B[0m";
	public static final String YELLOW = "\u001B[33m";

    public static void main(String [] args){

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println(RESET);
			System.out.flush();
		}));

		clearScreen();
		intro();
		File file = new File("Task.txt");
		try {

			if(file.createNewFile()) { //if successfully created 
				System.out.println("File Created: " + file.getName());
			}else {
				System.out.println("File Found: " + file.getName());
			}
		}catch(Exception e) {
			System.out.println("File error: " + e);
		}
		
		options(file);	

		
	}

	public static void intro(){
		
	String asciiArt = """
  _______         _        __  __                                            
 |__   __|       | |      |  \\/  |                                           
    | | __ _ ___ | | __   | \\  / | __ _ _ __   __ _  __ _  ___ _ __          
    | |/ _` / __|| |/ /   | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|         
    | | (_| \\__  |   <    | |  | | (_| | | | | (_| | (_| |  __/ |            
    |_|\\__,_|___/|_|\\_\\   |_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_|            
                                                     __/ |                   
                                            	   |___/                    
        """;
		System.out.println(RESET + asciiArt);
	}


	public static void options(File file) {

		// System.out.println("\n1.Print Tasks 2.Add Task 3.Remove Task 4.End Session" );
		// System.out
		Scanner sc = new Scanner(System.in);
		
		try{
			System.out.print("\nGoogle:\\Coding\\TaskManager:$ " + YELLOW);
	
			String usersOption = sc.nextLine();
			// sc.close();
			System.out.println(RESET);
			if(usersOption.equalsIgnoreCase("print")){
				printTasks(file);
			}else if(usersOption.equalsIgnoreCase("add")) {
				addTask(file);
			}else if(usersOption.equalsIgnoreCase("remove")){
				// removeTask(file);
			}else if(usersOption.equalsIgnoreCase("exit")) {
				System.out.println("--- Session Ended --- ");
				System.exit(0);
			}else if(usersOption.equalsIgnoreCase("help")){
				help(file);
			}else{
				System.out.println( RED + "\"" + usersOption + "\" is not recognized as an internal or external command, \noperable program or batch file.\n" + RESET);
				options(file);
			}
			
		}catch(Exception e){
			options(file);
		}
	}

	public static void help(File file){
		System.out.println("\nCommand Options");
		System.out.println("\n:$ Print\n:$ Add\n:$ Remove\n:$ exit\n" );
		options(file);
	}

    public static void printTasks(File file) {
		try {
			Scanner fileReader = new Scanner(file);
			System.out.println("Your tasks: ");

			// System.out.println(fileReader.hasNextLine());
			while(fileReader.hasNextLine()) {
				String data = fileReader.nextLine();
				System.out.println(data);
			}
		}catch(Exception e) {
			System.out.println("Failed to create file: " + e);
		}
		options(file);
	}


    public static void addTask(File file) {
		
		Scanner sc = new Scanner(System.in);
		
		try {
//			if(file.createNewFile())
			
			FileWriter myWriter = new FileWriter(file, true);
			String task = " ";
			
			while(!task.isEmpty()) {
				// System.out.println("Enter task: " + taskItemNum);
				task = sc.nextLine();
				myWriter.write(task + "\n");
			}
			// sc.close();
			myWriter.close();
			// printTasks(file);
			
		}catch(Exception e ) {
			System.out.println("Failed to add task" + e);
			options(file);
		}
		options(file);
	}

public static void clearScreen() {
    try {
        if (System.getProperty("os.name").contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            // For Mac/Linux
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    } catch (Exception e) {
        // Handle errors
    }
}

    
}
