package taskmanagerencryption;

public class Task {

    private int taskId;
	private String taskName = "";
	// private String taskDes = "";
	private boolean taskStatus = false;
	
	
	Task(int taskId, String taskName, boolean taskStatus){
		this.taskId = taskId;
		this.taskName = taskName;
		// this.taskDes = taskDes;
		this.taskStatus = taskStatus;
	}
	
	public int getTaskId() {
		return taskId; 
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	// public String getTaskDes() {
	// 	return taskDes;
	// }
	// public void setTaskDes(String taskDes) {
	// 	this.taskDes = taskDes;
	// }
	public boolean getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(boolean taskStatus) {
		this.taskStatus = taskStatus;
	}
}

