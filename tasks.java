// package taskmanagerEncryption;

public class tasks {

    private int taskIndex;
	private String taskName = "";
	private String taskDes = "";
	private boolean taskStatus;
	
	
	tasks(int taskIndex, String taskName, String taskDes, boolean taskStatus){
		this.taskIndex = taskIndex;
		this.taskName = taskName;
		this.taskDes = taskDes;
		this.taskStatus = taskStatus;
	}
	
	public int getTaskIndex() {
		return taskIndex; 
	}
	
	public void setTaskIndex(int taskIndex) {
		this.taskIndex = taskIndex;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDes() {
		return taskDes;
	}
	public void setTaskDes(String taskDes) {
		this.taskDes = taskDes;
	}
	public boolean getTaskStatus(boolean taskStatus) {
		return taskStatus;
	}
	public void setTaskStatus() {
		this.taskStatus = taskStatus;
	}
}

