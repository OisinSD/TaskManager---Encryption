package taskmanagerencryption;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
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
	private static SecretKeySpec myKey;
	private static String PK; 
    public static void main(String [] args){

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println(RESET);
			System.out.flush();
		}));

		clearScreen();
		intro();
		scanFile(login());
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

	private static String login(){

		System.out.print("\n--Login--\nPassword: ");
		Scanner sc = new Scanner(System.in);
	String userInputPassword = "";
		try{
			userInputPassword = sc.nextLine();
			byte[] keyBytes = userInputPassword.getBytes(StandardCharsets.UTF_8);
			myKey = new SecretKeySpec(keyBytes, "DES");
			

			if(toHexString(getSHA(userInputPassword)).equals("1af059c8517be14825bf1b82fc75801c667679193a8a2214dcadb788f846ca99")){
				PK = userInputPassword;
				System.out.println(GREEN + "CORRECT" + RESET);
				return userInputPassword;
			}else{
				System.out.println(RED + "INCORRECT" + RESET);
				System.exit(0);
			}
		}catch(Exception e){
			System.out.println("ERROR not working?\n " + e);
		}

		return userInputPassword;
	}

	private static void scanFile(String privateKey){
		
		try{
			Scanner fileReader = new Scanner(file);
			int dataIndex= 0;
		
			while(fileReader.hasNextLine()){
				String data = fileReader.nextLine();

				byte[] encryptedBytes = Base64.getDecoder().decode(data);
				byte[] privateKeyBytes = privateKey.getBytes(StandardCharsets.UTF_8);
                byte[] decryptedBytes = new byte[encryptedBytes.length];
				for(int i = 0; i < encryptedBytes.length; i++){
					decryptedBytes[i] = (byte) (encryptedBytes[i] ^ privateKeyBytes[i % privateKeyBytes.length]);
				}
                myTask.add(new Task(dataIndex, new String(decryptedBytes, StandardCharsets.UTF_8), false));
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
	
	public static void saveTasks(){
			try{
			try (FileWriter storingInFile = new FileWriter("Task.txt", false)) { 
					for (Task t : myTask) {
						
						byte[] inputBytes = t.getTaskName().getBytes(StandardCharsets.UTF_8);
						byte[] keyBytes = PK.getBytes(StandardCharsets.UTF_8);
						byte[] encrypted = new byte[inputBytes.length];
						
						for(int i = 0; i < inputBytes.length; i++){
            				encrypted[i] = (byte) (inputBytes[i] ^ keyBytes[i % keyBytes.length]);
						}
						
						// byte[] textEncrypted = desCipher.doFinal(t.getTaskName().getBytes("UTF8"));

						storingInFile.write(new String(Base64.getEncoder().encodeToString(encrypted)) + "\n");
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
	
	public static void help(){
		System.out.println("\nCommand Options");
		System.out.println("\n:$ Print\n:$ Add\n:$ Remove\n:$ exit\n" );
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

	public static byte[] getSHA(String input) throws NoSuchAlgorithmException
		{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			return md.digest(input.getBytes(StandardCharsets.UTF_8));
		}
		
	public static String toHexString(byte[] hash)
	{
		BigInteger number = new BigInteger(1, hash);
		StringBuilder hexString = new StringBuilder(number.toString(16));

		while (hexString.length() < 64)
		{
			hexString.insert(0, '0');
		}
		return hexString.toString();
	}
}
