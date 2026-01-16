package taskmanagerencryption;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Main{
	
	public static final String RED = "\u001B[31m";
	public static final String RESET = "\u001B[0m";
	public static final String YELLOW = "\u001B[33m";
	public static final String GREEN = "\u001B[32m";
	public static ArrayList<Task> myTask = new ArrayList<>();
	public static final File file = new File("Task.txt");
	private static byte[] key;
	private static SecretKeySpec myKey;

    public static void main(String [] args){

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println(RESET);
			System.out.flush();
		}));

		clearScreen();
		intro();
		login();
		scanFile();
		options();
		
	}

	public static void intro(){	
		String asciiArt = """
  _______          _        __  __                                            
 |__   __|        | |      |  \\/  |                                           
    | | __ _ ___  | | __   | \\  / | __ _ _ __   __ _  __ _  ___ _ __          
    | |/ _` / __| | |/ /   | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|         
    | | (_| \\__   |   <    | |  | | (_| | | | | (_| | (_| |  __/ |            
    |_|\\__,_|___/ |_|\\_\\   |_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_|            
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

	private static void login(){
		System.out.println(GREEN + "Password = tmanager" + RESET);
		System.out.print("--Login--\nPassword: ");
		Scanner sc = new Scanner(System.in);

		try{
			String userInputPassword = sc.nextLine();
			byte[] keyBytes = userInputPassword.getBytes("UTF_8");
			myKey = new SecretKeySpec(keyBytes, "DES");
			

			if(toHexString(getSHA(userInputPassword)).equals("1af059c8517be14825bf1b82fc75801c667679193a8a2214dcadb788f846ca99")){
				System.out.println(GREEN + "CORRECT" + RESET);
				return;
			}else{
				System.out.println(RED + "INCORRECT" + RESET);
				System.exit(0);
			}
		}catch(Exception e){
			System.out.println("ERROR" + e);
		}
	}

	private static void scanFile(){
		try{
			Scanner fileReader = new Scanner(file);
			int dataIndex= 0;
			
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, myKey);

			
			while(fileReader.hasNextLine()){
				String data = fileReader.nextLine();
				byte[] dencryptedBytes = cipher.doFinal(data.getBytes());
				myTask.add(new Task(dataIndex, dencryptedBytes.toString(), false));
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
				removeTask();
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

	public static void printTasksForRemoveMethod(){
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
	}

	public static void saveTasks(){
		try{
			KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
            // SecretKey myDesKey = keygenerator.generateKey();

            // Creating object of Cipher
            Cipher desCipher = Cipher.getInstance("DES");

		try (FileWriter storingInFile = new FileWriter("Task.txt", false)) { 
				for (Task t : myTask) {
	
					desCipher.init(Cipher.ENCRYPT_MODE, myKey);
					byte[] textEncrypted = desCipher.doFinal(t.getTaskName().getBytes("UTF8"));

					storingInFile.write(new String(textEncrypted) + "\n");
				}
				System.out.println(GREEN + "Tasks saved successfully!" + RESET);
			}
		}catch(Exception e){
			System.out.println("Save Failed" + e);
		}
		options();
	}

	public static void removeTask(){
		Scanner sc = new Scanner(System.in);
		try{
			printTasksForRemoveMethod();
			System.out.print("\nIndex of item removing: ");
			int removeFromListIndex = sc.nextInt(); 
			int currentIndex = 0;

				if(removeFromListIndex > -1 && removeFromListIndex < myTask.size()){
					myTask.remove(removeFromListIndex);
					saveTasks();
					System.out.println( GREEN + "Removed and saved successfully" + RESET);
				}else{
					System.out.println("Invalid index in current list - Try Again");
					removeTask();
				}
		}catch(Exception e){
			System.out.println(RED + "Unable to remove task: " + e );
		}
		options();
	}

	public static byte[] getSHA(String input) throws NoSuchAlgorithmException
		{
			// Static getInstance method is called with hashing SHA
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			// digest() method called
			// to calculate message digest of an input
			// and return array of byte
			return md.digest(input.getBytes(StandardCharsets.UTF_8));
		}
		
	public static String toHexString(byte[] hash)
	{
		// Convert byte array into signum representation
		BigInteger number = new BigInteger(1, hash);

		// Convert message digest into hex value
		StringBuilder hexString = new StringBuilder(number.toString(16));

		// Pad with leading zeros
		while (hexString.length() < 64)
		{
			hexString.insert(0, '0');
		}

		return hexString.toString();
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
