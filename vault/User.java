package vault;
import java.util.Date;
import java.util.ArrayList;
import java.math.BigInteger;


class User{
    String name;
    String userName;
    String password;
    int id;
    byte roleId;
    Date createdDate;
    User(int id,String name,String userName,String password){
        this(id,name,userName,password,(byte)1);
    }
    User(int id,String name,String userName,String password,byte roleId){
     this(id,name,userName,password,roleId,new Date());
    }
    
    User(int id,String name,String userName,String password,byte roleId,Date createdDate){
    	   this.name=name;
           this.password=password;
           this.userName=userName;
           this.id=id;
           this.roleId=roleId;
    }
    
    void display(){
        System.out.println("Name : "+name+"\tUserName : "+userName+"\t Id : "+id);
    }
    void updateName(String name){
        this.name=name;
    }
    void updatePassword(String password){
        this.password=password;
    }
}

class Customer extends User{
    RSA rsa;
    ArrayList<Application> ownApplication=new ArrayList<>();
    ArrayList<Application> sharedWithMe=new ArrayList<>();
    Trash trash=new Trash();
    boolean isLocked;
    Customer(int id,String name,String userName,String password,byte roleId,Date createdDate,BigInteger n,BigInteger e,BigInteger d){
       this(id,name,userName,password,roleId,createdDate,n,e,d,false);
    
    }
    Customer(int id,String name,String userName,String password,byte roleId,Date createdDate,BigInteger n,BigInteger e,BigInteger d,boolean isLocked){
       this(id,name,userName,password,roleId,createdDate,new RSA(n,e,d),isLocked);
    
    }
    Customer(int id,String name,String userName,String password,byte roleId,Date createdDate,RSA rsa,boolean isLocked){
       super(id,name,userName,password,roleId,createdDate);
        this.createdDate=createdDate;
        this.rsa=rsa;
        this.isLocked=isLocked;
    }
    Customer(User user,RSA rsa,boolean isLocked){
        super(user.id,user.name,user.userName,user.password,user.roleId,user.createdDate);
        this.rsa=rsa;
        this.isLocked=isLocked;
    }
    void viewAllApplication(){
        for(int i=0;i<ownApplication.size();i++){
            System.out.print((i+1)+". ");
            ownApplication.get(i).display();
        }
    }
    void viewAllApplicationShort(){
        for(int i=0;i<ownApplication.size();i++){
            System.out.println((i+1)+". Application Type : "+ownApplication.get(i).applicationType+"\tUserName : "+ownApplication.get(i).userName);
        }
    }
    
    void viewPassword(int index){
        System.out.println("----------------------------------\nPassword : "+rsa.decrypt(ownApplication.get(index).displayPassword())+"\n----------------------------------");
    }
    void addApplication(Application application){
        ownApplication.add(application);
    }
    void deleteAccount(int index,String comment){
        trash.addAccount(new deletedApplication(ownApplication.get(index),new Date(),comment));
        ownApplication.remove(index);
    }
    void viewSharedByMe(){
        int count=0;
        for(int i=0;i<ownApplication.size();i++){
            if(ownApplication.get(i).isShared){
                System.out.println((i+1)+". Application Type : "+ownApplication.get(i).applicationType+"\tUserName : "+ownApplication.get(i).userName);
                count++;
            }
        }
        if(count==0){
            System.out.println("-------- You didn't shared any applications ---------------");
        }
    }
    void viewSharedWithMe(){
        for(int i=0;i<sharedWithMe.size();i++){
            System.out.print((i+1)+". ");
            if(sharedWithMe.get(i)!=null) {
            sharedWithMe.get(i).display();
            }
            else {
            	System.out.println("Errrrrrrrrrrrrrrrrrrrrrrr");
            }
        }
    }
    int search(String appType){
        for(int i=0;i<ownApplication.size();i++){
            if(ownApplication.get(i).applicationType.equals(appType)){
                return i;
            }
        }
        return -1;
    }
    void share(String userName,Application app ){
    	app.isShared=true;
        app.sharedList.add(userName);
    }
    void viewTrash(){
        trash.viewAllAccount();
    }
    
    void viewTrashShort() {
    	trash.viewAllAccountShort();
    }
    void deletePermanently(int index){
        trash.deletePermanently(index);
    }
        boolean isApplicationEmpty(){
        return ownApplication.size()==0;
    }
    void restore(int appIndex){
    	deletedApplication app=trash.trashList.get(appIndex);
        ownApplication.add(new Application(app.appId,app.applicationType,app.userName,app.encryptedPassword,app.createdDate,app.description,app.ownerId));
    }
 
    int findApplicationIndex(int appId) {
		for(int i=0;i<ownApplication.size();i++) {
			if(ownApplication.get(i).appId==appId) {
				return i;
			}
		}
		return -1;
	}
    
	
}

class Application{
	int appId;
    String applicationType;
    String userName;
    String description;
    BigInteger encryptedPassword;
    int ownerId;
    boolean isShared;
    Date createdDate;
    ArrayList<String> sharedList;
    Application(int appId,String appType,String userName,BigInteger encryptedPassword,Date date,String description,int ownerId){
        this(appId,appType,userName,encryptedPassword,date,description,ownerId,false,new ArrayList<String>());
    }
    Application(int appId,String appType,String userName,BigInteger encryptedPassword,Date date,String description,int ownerId,boolean isShared,ArrayList<String> sharedList){
    	this.appId=appId;
        this.applicationType=appType;
        this.userName=userName;
        this.description=description;
        this.encryptedPassword=encryptedPassword;
        this.createdDate=date;
        this.ownerId=ownerId;
        this.isShared=isShared;
        this.sharedList=sharedList;
    }
    void display(){
        System.out.println("Application type : "+applicationType+"\tUserName : "+userName+"\t Created Date : "+createdDate+"\nDescription : "+description+"\t Password : **********"+"\t isShared : "+isShared);
        if(isShared){
            System.out.print("Shared customer Id's : ");
        for(int i=0;i<sharedList.size();i++){
            System.out.print(sharedList.get(i)+",");
        }
        System.out.println();
        }
    }
    BigInteger displayPassword(){
        return encryptedPassword;
    }

void updateApplicationName(String name){
    this.applicationType=name;
}
void updateUserName(String name){
    this.userName=name;
}
void updatePassword(BigInteger newPassword){
    this.encryptedPassword=newPassword;
}
}

class deletedApplication extends Application{
Date dateOfDeletion;
String comment;

deletedApplication(Application app,Date dateOfDeletion,String comment){
    super(app.appId,app.applicationType,app.userName,app.encryptedPassword,app.createdDate,app.description,app.ownerId);
    this.isShared=app.isShared;
    this.ownerId=app.ownerId;
    this.dateOfDeletion=dateOfDeletion;
    this.comment=comment;
}
deletedApplication(int id,String appType,String userName,BigInteger encryptedPassword,Date createdDate,String description,int ownerId,boolean isShared,ArrayList<String> sharedList,Date dateOfDeletion,String comment){
      super(id,appType,userName,encryptedPassword,createdDate,description,ownerId);
    this.isShared=isShared;
    this.ownerId=ownerId;
    this.dateOfDeletion=dateOfDeletion;
    this.comment=comment;
}
void display(){
        super.display();
        System.out.println("Date Of Deletion : "+dateOfDeletion+"\t Comment : "+comment);
}
}


class Trash{
    ArrayList<deletedApplication> trashList=new ArrayList<>();
    Trash(){
        trashList.trimToSize();
    }
    boolean isTrashEmpty(){
        return trashList.size()==0;
    }
    void addAccount(deletedApplication acc){
        trashList.add(acc);
    }
    void viewAllAccount(){
        for(int i=0;i<trashList.size();i++){
            if(trashList.get(i)!=null){
            System.out.print((i+1)+". ");
            trashList.get(i).display();
            }
        }
    }
    void viewAllAccountShort(){
                for(int i=0;i<trashList.size();i++){
            if(trashList.get(i)!=null){
            System.out.println((i+1)+". "+"Account Type : "+trashList.get(i).applicationType+"\t UserName : "+trashList.get(i).userName);
            }
        }
    }
    void viewPassword(int index){
        trashList.get(index).displayPassword();
    }
    void deletePermanently(int index){
        trashList.remove(index);
    }
    void viewAllApplicationShort(){
        for(int i=0;i<trashList.size();i++){
            System.out.println((i+1)+". Application Type : "+trashList.get(i).applicationType+"\tUserName : "+trashList.get(i).userName);
        }
    }
}
