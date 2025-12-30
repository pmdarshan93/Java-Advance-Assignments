package vault;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Vault {
	private DataBaseOperations dbOperation = new DataBaseOperations();
	private ArrayList<User> userList = new ArrayList<>();
	private User currentUser;
	private Customer currentCustomer;
	private static final Logger logger=LogManager.getLogger();
	
	public void viewAllUser() {
		logger.info(currentUser.id+" accessed view all user");
		for (int i = 0; i < userList.size(); i++) {
			System.out.print((i + 1) + ". ");
			userList.get(i).display();
		}
	}

	int findUserId(String userName) {
		logger.info(" find user id for user name"+userName);
		if(userName!="") {
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).userName.equals(userName)) {
				return userList.get(i).id;
			}
		}
		}
		return -1;
	}

	int findUserIndex(int userId) {
		logger.info(" accessed find user Index for user Id "+userId);
		if(userId>-1) {
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).id == userId) {
				return i;
			}
		}
		}
		return -1;
	}

	public int login(String userName, String password) {
		int loginId=dbOperation.login(userName, password);
		if(loginId!=-1) {
				storeUsers();
				currentUser=dbOperation.storeUser(findUserId(userName));
				logger.info(currentUser.id+" loginned");
				
		}
			return loginId;
	}

	public String getName() {
		logger.info(currentUser.id+ " get name.");
	return currentUser.name;	
	}
	
	public boolean userNameExist(String userName) {
		logger.info("Checked exist of userName : "+userName);
		return dbOperation.userNameExist(userName);
	}

	public int addUser(String name, String userName, String password, int role) {
		logger.info(currentUser.id+" new user added "+userName);
		return dbOperation.addUser(name, userName, password, role);
	}

	public void addCustomer(int id, BigInteger n, BigInteger e, BigInteger d, byte isLocked) {
		logger.info("New customer added id : "+id);
		dbOperation.addCustomer(id, n, e, d, isLocked);
	}

	public void addAudit(int action, int id) {
		dbOperation.addAudit(action, id);
	}
	
	public void storeUsers() {
		userList=dbOperation.storeUsers();
		logger.info("User list stored");
	}
	
	public int getRole(int id) {
		return dbOperation.getRole(id);
	}
	
	public void viewAudit() {
		ResultSet ans=dbOperation.viewAudit();
		if(ans!=null) {
			printAudit(ans);
		}
	}
	
	public int auditOfUser(String userName) {
		int userId=findUserId(userName);
		ResultSet ans=dbOperation.auditOfUser(userId);
		if(ans!=null) {
			printAudit(ans);
			return 0;
		}
		return -1;
	}
	
	public void auditOfAction(int action) {
		ResultSet ans=dbOperation.auditOfAction(action);
		printAudit(ans);
	}
	
	public void printAudit(ResultSet ans) {
		try {
			while(ans.next()) {
			System.out.println(
					Audit.arr[ans.getInt(2) - 1]+ " at " + ans.getString(4) + " by " + ans.getInt(3));
}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public int unlock(String userName) {
		int userId=findUserId(userName);
		if(userId==-1)return -1;
		if(checkLockStatus(userId)) {
			dbOperation.unlock(userId);
			logger.info(currentUser.id+" unlocked customer : "+userId);
			return 1;
		}else {
			return 0;
		}
	}
	
	public void lockCustomer(int loginId) {
		logger.info(currentUser.id+" locked customer");
		dbOperation.lock(loginId);
	}
	
	public boolean checkLockStatus(int userId) {
		logger.info(currentUser.id+" lock status checked");
		return dbOperation.checkLockStatus(userId);
	}
	
	public void storeCustomer(int loginId){
		logger.info(currentUser.id+" customer stored");
		currentCustomer=dbOperation.storeCustomer(loginId);
	}

	public boolean isCurrentCustomerHasApplication() {
		logger.info(currentUser.id+" checked application is empty");
		return currentCustomer.isApplicationEmpty();
	}
	
	public void viewAllApplicationOfCustomer() {
		logger.info(currentUser.id+" viewed all application");
		currentCustomer.viewAllApplication();
	}
	
	public boolean searchApplicationInCustomer(String application) {
		logger.info(currentUser.id+" searched application "+application);
		return currentCustomer.search(application)!=-1;
	}
	
	public void displayApplicationOfCustomer(String application) {
		logger.info(currentUser.id+" viewed applciation "+application);
		currentCustomer.ownApplication.get(currentCustomer.search(application)).display();
	}
	
	public void viewNamesOfApplicationOfCustomer() {
		logger.info(currentUser.id+" viewed all names of application");
		currentCustomer.viewAllApplicationShort();
	}
	
	public int totalNoOfApplication() {
		logger.info(currentUser.id+" accessed no of applications");
		return currentCustomer.ownApplication.size();
	}
	
	public boolean checkPasswordOfCustomer(String password) {
		logger.info(currentUser.id+" checked password for input "+password);
		return currentCustomer.password.equals(password);
	}
	
	public void viewPasswordOfApplication(int index) {
		logger.info(currentUser.id+" accessed password of application index : "+index);
		currentCustomer.viewPassword(index);
	}
	
	public void addApplicationToCustomer(String appName,String userName,String password,Date date,String description) {
		logger.info(currentUser.id+" new application added : "+appName);
		dbOperation.addApplication(currentCustomer, appName,userName,currentCustomer.rsa.encrypt(password), date,description,currentCustomer.id);
	}
	
	public void deleteApplication(int index,String reason) {
		logger.info(currentUser.id+" deleted application index :"+index);
		dbOperation.deleteApplication(currentCustomer.ownApplication.get(index), reason);
		currentCustomer.deleteAccount(index, reason);
	}
	
	public void updateApplicationOfCustomer(String field,int appIndex,String newValue) {
		dbOperation.updateApplication(currentCustomer, field,currentCustomer.ownApplication.get(appIndex).appId, newValue);
		if(field.equals("name")) {
			logger.info(currentUser.id+" updated application name : "+newValue+" app Index : "+appIndex);
			currentCustomer.ownApplication.get(appIndex).updateApplicationName(newValue);
		}
		else if(field.equals("username")) {
			logger.info(currentUser.id+" updated application username : "+newValue+" app index : "+appIndex);
			currentCustomer.ownApplication.get(appIndex).updateUserName(newValue);
		}
		else if(field.equals("password")) {
			logger.info(currentUser.id+" updated application password : "+newValue+" app Index : "+appIndex);
			currentCustomer.ownApplication.get(appIndex).updatePassword(currentCustomer.rsa.encrypt(newValue));
		}
	}
	
	boolean isCustomer(String userName,int loginId) {
		int userId=findUserId(userName);
		logger.info(currentUser.id+" checked exist of customer "+userName);
		return userId!=-1 && userId!=loginId && userList.get(findUserIndex(userId)).roleId==2;
	}
	
	void shareToCustomer(String customerName,int appIndex){
		int customerId=findUserId(customerName);
		int appId=currentCustomer.ownApplication.get(appIndex).appId;
		System.out.println(customerId+" "+appId+" ----------");
		dbOperation.share(customerId,appId);
		currentCustomer.share(userList.get(findUserIndex(customerId)).userName,currentCustomer.ownApplication.get(appIndex));
		logger.info(currentUser.id+" shared application appId : "+appId+" with customer "+customerId);
	}
	
	boolean isCustomerSharedListEmpty() {
		logger.info(currentUser.id+" checked shared with me list is empty");
		return currentCustomer.sharedWithMe.size()==0;
	}
	
	void viewApplicationSharedByCustomer() {
		logger.info(currentUser.id+" checked shared by me list is empty ");
		currentCustomer.viewSharedByMe();
	}
	void viewApplicationSharedWithCustomer() {
		logger.info(currentUser.id+" viewed applciation share with customer");
		currentCustomer.viewSharedWithMe();
	}
	
	void viewAllApplicationInTrash() {
		logger.info(currentUser.id+" viewed all application in trash");
		currentCustomer.viewTrash();
	}
	
	void viewNamesOfTrashApplicationOfCustomer() {
		logger.info(currentUser.id+" viewed all application in trash");
		currentCustomer.viewTrashShort();
	}
	
	int totalNoTrashApplication() {
		logger.info(currentUser.id+" accessed total no of trash application");
		return currentCustomer.trash.trashList.size();
	}
	
	void restore(int index) {
		logger.info(currentUser.id+" restored application "+index);
		dbOperation.restore(currentCustomer, index);
		currentCustomer.restore(index);
		permanentlyDelete(index);
	}
	
	void permanentlyDelete(int index) {
		logger.info(currentUser.id+" deleted application "+index);
		dbOperation.permanentlyDelete(currentCustomer.trash.trashList.get(index).appId);
		currentCustomer.deletePermanently(index);
	}
	
	void updateUser(String field,String newValue) {
		dbOperation.updateUser( field, currentCustomer.id, newValue);
		if(field.equals("name")) {
			logger.info(currentUser.id+" updated User Name "+newValue);
			currentCustomer.updateName(newValue);
		}
		else if(field.equals("password")){
			logger.info(currentUser.id+" updated vault password "+newValue);
			currentCustomer.updateName(newValue);
		}
	}
}
