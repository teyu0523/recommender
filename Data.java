import java.util.*;



public class Data {
	private String[] itemList = null;
	private List<String> userList = new LinkedList<String>();
	private List<double[]> users = new LinkedList<double[]>();

	public String[] getItemList(){
		return itemList;
	}

	public List<String> getUserList(){
		return userList;
	}

	public List<double[]> getUsers(){
		return users;
	}

	public void setItemList(String[] itemList){
		this.itemList = itemList;
	}

	public void setUserList(List<String> userList){
		this.userList = userList;
	}

	public void setUsers(List<double[]> users){
		this.users = users;
	}

}