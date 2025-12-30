package vault;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataBaseOperations {
	DataBaseConnection dbconnection = DataBaseConnection.getInstant();
	Connection connection = dbconnection.getConnection();
	SQLQuery queries = new SQLQuery();
	private static final Logger logger=LogManager.getLogger();

	public int login(String userName, String password) {
		try {
			PreparedStatement statement = connection.prepareStatement(queries.login);
			statement.setString(1, userName);
			statement.setString(2, password);
			ResultSet ans = statement.executeQuery();
			logger.info("queried from DBOperation for username : "+userName+" password : "+password);
			if (ans.next()) {
				logger.info("Login Successfully returned Id "+ans.getInt("Id"));
				return ans.getInt("Id");
			} else {
				logger.info("Login failed");
				return -1;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		return -1;

	}

	boolean userNameExist(String name) {
		try {
			PreparedStatement statement = connection.prepareStatement(queries.userNameExist);
			statement.setString(1, name);
			ResultSet ans = statement.executeQuery();
			return ans.next();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		return false;
	}

	public int addUser(String name, String userName, String password, int role) {
		try {
			PreparedStatement statement = connection.prepareStatement(queries.addUser, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, name);
			statement.setString(2, userName);
			statement.setString(3, password);
			statement.setInt(4, role);
			statement.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
			int ans = statement.executeUpdate();
			logger.info("Queried from dbOperation to add new user ");
			if (ans > 0) {
				ResultSet id = statement.getGeneratedKeys();
				if (id.next()) {
					logger.info("Added User query SUccessfully Id :"+id.getInt(1));
					return id.getInt(1);
				}
			}
		} catch (

		SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		return 0;
	}

	void addCustomer(int id, BigInteger n, BigInteger e, BigInteger d, byte isLocked) {
		try {
			PreparedStatement statement = connection.prepareStatement(queries.addCustomer);
			statement.setInt(1, id);
			statement.setString(2, n.toString());
			statement.setString(3, e.toString());
			statement.setString(4, d.toString());
			statement.setByte(5, isLocked);
			int ans = statement.executeUpdate();
			logger.info("Queried from dbOperation toAdd new customer id : "+id);
		} catch (SQLException e2) {
			logger.fatal(e2.getMessage());
			System.out.println(e2.getMessage());
		}
	}

	void addAudit(int action, int id) {
		try {
			PreparedStatement statement = connection.prepareStatement(queries.addAudit);
			statement.setInt(1, action);
			statement.setInt(2, id);
			statement.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
			int rowAffected = statement.executeUpdate();
			logger.info("Queried from dbOperation to add audit action : "+action+" id "+id);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	int getRole(int id) {
		try {
			PreparedStatement statement = connection.prepareStatement(queries.getRole);
			statement.setInt(1, id);
			ResultSet ans = statement.executeQuery();
			logger.info("Queried from dbOperation to get ROle of id : "+id);
			if (ans.next()) {
				return ans.getInt("role");
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		return -1;
	}

	User storeUser(int id) {
		try {
			PreparedStatement statement = connection.prepareStatement(queries.storeUser);
			statement.setInt(1, id);
			ResultSet ans = statement.executeQuery();
			
			logger.info("Queried from dbOperation to store user : "+id);
			if (ans.next()) {
				logger.info("Queried from dbOperation to store user successful");
				return new User(id, ans.getString("name"), ans.getString("userName"), ans.getString("password"),
						ans.getByte("role"), ans.getDate("createdDate"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		return null;
	}

	ArrayList<User> storeUsers() {
		try {
			PreparedStatement statement = connection.prepareStatement(queries.storeAllUsers);
			ResultSet ans = statement.executeQuery();
			ArrayList<User> userList = new ArrayList<>();
			logger.info("Queried from dbOperation to store userList");
			while (ans.next()) {
				userList.add(new User(ans.getInt("Id"), ans.getString("name"), ans.getString("userName"),
						ans.getString("password"), ans.getByte("role"), ans.getDate("createdDate")));
			}
			logger.info("Queried from dbOperation successfull");
			return userList;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		return null;
	}

	ResultSet viewAudit() {
		try {
			PreparedStatement countStatement = connection.prepareStatement(queries.countOfAuditList);
			ResultSet countAns = countStatement.executeQuery();
			logger.info("Queried from dbOperation to view audit");
			if (countAns.next()) {
				int count = countAns.getInt("count");
				if (count > 20) {
					PreparedStatement statement = connection.prepareStatement(queries.viewAuditList);
					ResultSet ans = statement.executeQuery();
					return ans;
				} else {
					PreparedStatement statement = connection.prepareStatement(queries.viewAuditList2);
					ResultSet ans = statement.executeQuery();
					return ans;
				}
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		return null;
	}

	ResultSet auditOfUser(int id) {
		PreparedStatement statement;
		try {
			logger.info("Queried from dbOperation to view audit of user id : "+id);
			statement = connection.prepareStatement(queries.auditOfUser);
			statement.setInt(1, id);
			ResultSet ans = statement.executeQuery();
			return ans;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		return null;
	}

	ResultSet auditOfAction(int action) {
		try {
			logger.info("Queried from dbOperation to view audit of action : "+action);
			PreparedStatement statement = connection.prepareStatement(queries.auditOfAction);
			statement.setInt(1, action);
			ResultSet ans = statement.executeQuery();
			return ans;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		return null;
	}

	boolean checkLockStatus(int userId) {
		try {
			logger.info("Queried from dbOperation to check lock status of user Id : "+userId);
			PreparedStatement statement = connection.prepareStatement(queries.checkLockStatus);
			statement.setInt(1, userId);
			ResultSet ans = statement.executeQuery();
			if (ans.next()) {
				return ans.getInt(1) == 1;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		return false;
	}

	void unlock(int userId) {
		try {
			logger.info("Queried from dbOperation to unlock user Id : "+userId);
			PreparedStatement statement = connection.prepareStatement(queries.unlock);
			statement.setInt(1, userId);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	Customer storeCustomer(int loginId) {
		try {
			Customer curr = null;
			PreparedStatement statement = connection.prepareStatement(queries.storeCustomer);
			User userDetail = storeUser(loginId);
			statement.setInt(1, loginId);
			ResultSet ans = statement.executeQuery();
			logger.info("Queried from dbOperation to store customer Data of id : "+loginId);
			if (ans.next()) {
				curr = new Customer(userDetail, new RSA(new BigInteger(ans.getString(2)),
						new BigInteger(ans.getString(3)), new BigInteger(ans.getString(4))), ans.getInt(5) == 1);
			} else {
				return null;
			}
			// store applications
			logger.info("Queried from dbOperation to store own application of customer id :"+loginId);
			curr.ownApplication = storeOwnApplication(loginId);
			// store shared with Me
			logger.info("Queried from dbOperation to store shared with me of customer id : "+loginId);
			curr.sharedWithMe = storeSharedWithMe(loginId);
			// store Trash
			logger.info("Queried from dbOperation to store trash of customer Id : "+loginId);
			curr.trash.trashList=storeTrashList(loginId);
			logger.info("Queried from dbOperation to store customer successful");
			return curr;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		return null;
	}

	ArrayList<Application> storeOwnApplication(int loginId) {
		ArrayList<Application> ownApplication = new ArrayList<>();
		try {
			PreparedStatement addApplication = connection.prepareStatement(queries.storeAllApplication);
			addApplication.setInt(1, loginId);
			ResultSet apps = addApplication.executeQuery();
			while (apps.next()) {
				ownApplication.add(storeApplication(apps));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		return ownApplication;
	}

	ArrayList<Application> storeSharedWithMe(int loginId) {
		PreparedStatement getApplication;
		ArrayList<Application> sharedWithMe = new ArrayList<>();
		try {
			PreparedStatement sharedWithMeStatement = connection.prepareStatement(queries.sharedWithMe);
			sharedWithMeStatement.setInt(1, loginId);
			ResultSet sharedWithMeAns = sharedWithMeStatement.executeQuery();
			while (sharedWithMeAns.next()) {
				getApplication = connection.prepareStatement(queries.getApplicationUsingAppId);
				getApplication.setInt(1, sharedWithMeAns.getInt(1));
				ResultSet sharedApp = getApplication.executeQuery();
				if (sharedApp.next()) {
					Application newApp = storeApplication(sharedApp);
					sharedWithMe.add(newApp);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		return sharedWithMe;

	}
	
	ArrayList<deletedApplication> storeTrashList(int loginId){
		ArrayList<deletedApplication> trashList=new ArrayList<>();
		try {
		PreparedStatement trashStatement = connection.prepareStatement(queries.trash);
		trashStatement.setInt(1, loginId);
		ResultSet trashListAns = trashStatement.executeQuery();
		while (trashListAns.next()) {
			trashList.add(storeDeletedApplication(trashListAns));
		}
		}catch(SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		return trashList;
	}

	Application storeApplication(ResultSet apps) {
		try {
			logger.info("Queried from dbOperation to store application appId "+apps.getInt(1));
			PreparedStatement shareStatement = connection.prepareStatement(queries.storeShareList);
			shareStatement.setInt(1, apps.getInt(1));
			ArrayList<String> sharedList = new ArrayList<>();
			ResultSet shareListAns = shareStatement.executeQuery();
			while (shareListAns.next()) {
				sharedList.add(getUserName(shareListAns.getInt(1)));
			}
			return new Application(apps.getInt(1), apps.getString(2), apps.getString(3),
					new BigInteger(apps.getString(5)), apps.getDate(8), apps.getString(4), apps.getInt(6),
					apps.getString(7).equals("Y"), sharedList);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	String getUserName(int id) {
		try {
			logger.info("Queried from dbOperation to get username id : "+id);
			PreparedStatement statement=connection.prepareStatement(queries.getUserName);
			statement.setInt(1, id);
			ResultSet name=statement.executeQuery();
			if(name.next()) {
				return name.getString(1);
			}
		}catch(SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		return null;
	}

	deletedApplication storeDeletedApplication(ResultSet apps) {
		PreparedStatement shareStatement;
		try {
			logger.info("Queried from dbOperation to store deleted application app id :"+apps.getInt(1));
			shareStatement = connection.prepareStatement(queries.storeShareList);
			shareStatement.setInt(1, apps.getInt(1));
			ArrayList<String> sharedList = new ArrayList<>();
			ResultSet shareListAns = shareStatement.executeQuery();
			while (shareListAns.next()) {
				sharedList.add(getUserName(shareListAns.getInt(1)));
			}
			return new deletedApplication(apps.getInt(1), apps.getString(2), apps.getString(3),
					new BigInteger(apps.getString(5)), apps.getDate(8), apps.getString(4), apps.getInt(6),
					apps.getString(7).equals("Y"), sharedList, apps.getDate(9), apps.getString(10));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	void lock(int loginId) {
		try {
			logger.info("Queried from dbOperation to lock customer id : "+loginId);
			PreparedStatement statement = connection.prepareStatement(queries.lock);
			statement.setInt(1, loginId);
			int rows = statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}

	}

	void addApplication(Customer c, String applicationType, String userName, BigInteger encryptedPassword,
			Date createdDate, String description, int ownerId) {
		try {
			logger.info("Queried from dbOperation to add application appName "+applicationType);
			PreparedStatement getId = connection.prepareStatement(queries.getAppId);
			ResultSet ans = getId.executeQuery();
			int appId;
			if (ans.next()) {
				appId = ans.getInt(1);
			} else {
				return;
			}
			Application app = new Application(appId, applicationType, userName, encryptedPassword, createdDate,
					description, ownerId);
			c.addApplication(app);
			PreparedStatement statement = connection.prepareStatement(queries.addApplication);
			statement.setString(1, app.applicationType);
			statement.setString(2, app.userName);
			statement.setString(3, app.description);
			statement.setString(4, app.encryptedPassword.toString());
			statement.setInt(5, app.ownerId);
			statement.setString(6, "N");
			statement.setDate(7, new java.sql.Date(app.createdDate.getTime()));
			statement.executeUpdate();
			logger.info("Queried from dbOperation add application successfully appId :"+appId);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}

	}

	void deleteApplication(Application app, String reason) {

		try {
			logger.info("Queried from dbOperation to delete app Id "+app.appId);
			PreparedStatement statement = connection.prepareStatement(queries.deleteApplication);
			PreparedStatement delStatement = connection.prepareStatement(queries.addDelete);
			logger.info("Queried from dbOperation to created deletedapplication appId "+app.appId);
			statement.setString(1, app.applicationType);
			statement.setString(2, app.userName);
			statement.setString(3, app.encryptedPassword.toString());
			statement.setInt(4, app.ownerId);
			statement.setDate(5, new java.sql.Date(app.createdDate.getTime()));
			int row = statement.executeUpdate();
			if (row > 0) {
				delStatement.setString(1, app.applicationType);
				delStatement.setString(2, app.userName);
				delStatement.setString(3, app.description);
				delStatement.setString(4, app.encryptedPassword.toString());
				delStatement.setInt(5, app.ownerId);
				delStatement.setString(6, app.isShared ? "Y" : "N");
				delStatement.setDate(7, new java.sql.Date(app.createdDate.getTime()));
				delStatement.setDate(8, new java.sql.Date(new java.util.Date().getTime()));
				delStatement.setString(9, reason);
				logger.info("Queried from dbOperation deleteion successful");
				logger.info("Queried from dbOperation insert into trash successful");
				delStatement.executeUpdate();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	void updateApplication(Customer current, String action, int appId, String newValue) {
		try {
			PreparedStatement statement = null;
			logger.info("Queried from dbOperation to update application appId : "+appId);
			if (action.equals("name")) {
				statement = connection.prepareStatement(queries.updateName);
				logger.info("Queried from dbOperation update name appId : "+appId+" new value : "+newValue);
			} else if (action.equals("username")) {
				logger.info("Queried from dbOperation to update application  user name of appId :"+appId+" username "+newValue);
				statement = connection.prepareStatement(queries.updateApplicationUserName);
			} else if (action.equals("password")) {
				statement = connection.prepareStatement(queries.updatePassword);
				BigInteger newPassword = current.rsa.encrypt(newValue);
				newValue = newPassword.toString();
				logger.info("Queried from dbOperation to update application password of appId : "+appId+" password "+newValue);
			}
			if (statement != null) {
				statement.setString(1, newValue);
				statement.setInt(2, appId);
				statement.executeUpdate();
			}
			logger.info("Queried from dbOperation to update successful");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}

	}

	void share( int cusIdToShare,int appId) {
		try {
			logger.info("Queried from dbOperation to share appId "+appId+" to customer "+cusIdToShare);
			PreparedStatement insertStatement = connection.prepareStatement(queries.share);
			PreparedStatement updateShareStatus = connection.prepareStatement(queries.shareStatus);
			insertStatement.setInt(1, cusIdToShare);
			insertStatement.setInt(2, appId);
			updateShareStatus.setInt(1, appId);
			insertStatement.executeUpdate();
			updateShareStatus.executeUpdate();
			logger.info("Queried from dbOperation to share successful");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	void restore(Customer currentCustomer, int index) {
//	addApplication(Customer c, String applicationType, String userName, BigInteger encryptedPassword,
//			Date createdDate, String description, int ownerId) 
		try {
			PreparedStatement statement = connection.prepareStatement(queries.restore);
			statement.setInt(1, currentCustomer.trash.trashList.get(index).appId);
			ResultSet ans = statement.executeQuery();
			logger.info("Queried from dbOperation to restore appId "+currentCustomer.trash.trashList.get(index).appId);
			if (ans.next()) {
				addApplication(currentCustomer, ans.getString(2), ans.getString(3), new BigInteger(ans.getString(5)),
						ans.getDate(8), ans.getString(4), ans.getInt(6));
			}
			logger.info("Queried from dbOperation to restore successful");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}

	}

	void permanentlyDelete(int appId) {
		try {
			logger.info("Queried from dbOperation to delete permanenetly appId : "+appId);
			PreparedStatement statement = connection.prepareStatement(queries.deletePermanently);
			statement.setInt(1, appId);
			statement.executeUpdate();
			logger.info("Queried from dbOperation to deletion successful");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	void updateUser(String field, int loginId, String newValue) {
		PreparedStatement statement = null;
		try {
			logger.info("Queried from dbOperation to update User");
			if (field.equals("name")) {
				logger.info("Queried from dbOperation to update name of user Id : "+loginId);
				statement = connection.prepareStatement(queries.updateUsername);
			} else if (field.equals("password")) {
				logger.info("Queried from dbOperation to update vault password : "+loginId);
				statement = connection.prepareStatement(queries.updateUserPassword);
			} else {
				return;
			}
			statement.setString(1, newValue);
			statement.setInt(2, loginId);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
	}

}
