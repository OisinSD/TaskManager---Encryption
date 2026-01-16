package taskmanagerencryption;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;



public class Main{
	
	public static final String RED = "\u001B[31m";
	public static final String RESET = "\u001B[0m";
	public static final String YELLOW = "\u001B[33m";
	public static final String GREEN = "\u001B[32m";
	public static ArrayList<Task> myTask = new ArrayList<>();
	public static File file = new File("Task.txt");

    public static void main(String [] args){

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println(RESET);
			System.out.flush();
		}));

		clearScreen();
		intro();
		scanFile();
		options();
		
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
		System.out.println(RESET + "Task Manager\nCopyright \u00A9 Oisin Dillon. All rights reserved.\n" + asciiArt);
		// File file = new File("Task.txt");

		try {
			if(file.createNewFile()) { //if successfully created 
				System.out.println("File Created: " + file.getName());
			}else {
				System.out.println("File Found: " + file.getName());
			}
		}catch(Exception e) {
			System.out.println("File error: " + e);
		}
	}

	private static void scanFile(){
		try{
			Scanner fileReader = new Scanner(file);
			int dataIndex= 0;
		
			while(fileReader.hasNextLine()){
				String data = fileReader.nextLine();
				myTask.add(new Task(dataIndex, data, false));
				dataIndex++;
			}
		}catch(Exception e){
			System.out.println("UNABLE TO SCAN FILE" + e);
		}
	}

	public static void options() {
		try{
			Scanner sc = new Scanner(System.in);
			System.out.print("\nPS Google:\\Coding\\TaskManager:$ " + YELLOW);
			String usersOption = sc.nextLine();
			System.out.println(RESET);

			if(usersOption.equalsIgnoreCase("print")){
				printTasks();
			}else if(usersOption.equalsIgnoreCase("add")) {
				addTask();
			}else if(usersOption.equalsIgnoreCase("remove")){
				// removeTask(file);
			}else if(usersOption.equalsIgnoreCase("save")){
				saveTasks();
			}else if(usersOption.equalsIgnoreCase("exit")) {
				System.out.println("--- Session Ended --- ");
				System.exit(0);
			}else if(usersOption.equalsIgnoreCase("help")){
				help();
			}else{
				System.out.println( RED + "\"" + usersOption + "\" is not recognized as an internal or external command, \noperable program or batch file.\n" + RESET);
				options();
			}
		}catch(Exception e){
			options();
		}
	}


public static void addTask(){
	Scanner sc = new Scanner(System.in);
	System.out.print("Task " + myTask.size() + ": ");
	String taskName = sc.nextLine();

	if(taskName.equalsIgnoreCase("") || taskName.equalsIgnoreCase(" ")){
		options();
	}
	// System.out.print("\nTask Description: ");
	// String taskDes = sc.nextLine();

	myTask.add(new Task(myTask.size(), taskName, false));

	addTask(); //learn about stack overflows because I got advice that this recursion could cause one.
}




public static void printTasks(){
	if(myTask.size() < 1){
		System.out.println(RED + "No Tasks Currently in List" + RESET);
		options();
	}
	String status = "";
	for(Task t : myTask){
		if(t.getTaskStatus() == false){
			status = "Not-Complete";
		}else{
			status = "Complete";
		}
		System.out.println(t.getTaskId() + ": " + t.getTaskName() + " [" + status + "]");
	}
	options();
}

public static void saveTasks(){
	try{
	try (FileWriter storingInFile = new FileWriter("Task.txt", false)) { 
            for (Task t : myTask) {
                storingInFile.write(t.getTaskName() + "\n");
            }
            System.out.println(GREEN + "Tasks saved successfully!" + RESET);
        }
	}catch(Exception e){
		System.out.println("Save Failed" + e);
	}
	options();
}

    // public static void printTasks(File file) {
	// 	int taskIndex = 0;
	// 	try {
	// 		Scanner fileReader = new Scanner(file);
	// 		System.out.println("Items");
	// 		int dataLength = 0;

	// 		while(fileReader.hasNextLine()) {
	// 			String data = fileReader.nextLine();
	// 			System.out.println(taskIndex + ": " + data);
	// 			taskIndex++;
	// 			if(data.length() > dataLength){
	// 				dataLength = data.length();
	// 			}
	// 		}

	// 		for(int i = 0; i < dataLength + 3; i++){
	// 			System.out.print("-");
	// 		}
	// 		System.out.println("");

	// 	}catch(Exception e) {
	// 		System.out.println("Failed to create file: " + e);
	// 	}
	// 	options(file);
	// }


    // public static void addTask(File file) {
	// 	Scanner sc = new Scanner(System.in);
	// 	try{
	// 		FileWriter myWriter = new FileWriter(file, true);
	// 		String task = " ";
			
	// 		while(!task.isEmpty()) {
	// 			task = sc.nextLine();
	// 			if(!task.equals("")){
	// 				myWriter.write(task + "\n");
	// 			}
	// 		}
	// 		System.out.println(GREEN + "Tasks Added Successfully" + RESET);
	// 		myWriter.close();
			
	// 	}catch(Exception e ) {
	// 		System.out.println("Failed to add task" + e);
	// 		options(file);
	// 	}
	// 	options(file);
	// }


	// public static void removeTask(File file){
	// 	printTasks(file);
	// 	Scanner sc = new Scanner(System.in);
	// 	System.out.println(RED + "Please Specify the index of the task you want to remove: " + YELLOW);
	// 	try{
	// 		String removingThisItem = sc.nextInt();
	// 		if(removingThisItem){

	// 		}
	// 	}catch(Exception e ){
	// 		System.out.println("Error: " + e);
	// 	}
	// }


public static void help(){
		System.out.println("\nCommand Options");
		System.out.println("\n:$ Print\n:$ Add\n:$ Remove\n:$ exit\n" );
		options();
	}


	public static void clearScreen() {
		try {
			if (System.getProperty("os.name").contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				System.out.print("\033[H\033[2J");
				System.out.flush();
			}
		} catch (Exception e) {
			System.out.println("Cannot clear screen: " + e);
		}
	}
}
