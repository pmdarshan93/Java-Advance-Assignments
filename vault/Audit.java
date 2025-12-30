package vault;
import java.util.Date;
import java.util.ArrayList;

class Audit{
    static String[] arr=new String[]{"login","View ALl Account","New Admin added ","Search","Viewed Application ","Viewed All applications "," Viewed password ","Added application","Deleted application ","Entered Update module ","Updated application name ","Updated application userName ","Updated application password ","Shared application ","Viewed shared by me list ","Viewed Shared with me list","Viewed Trash","Viewed all trash Account","Permanently deleted application ","Closed Trash ","Updated Customer Name ","Updated vault password ","logged Out "};
    int action;
    Date dateOfAction;
    int id;
    Audit(int action,Date date,int id){
        this.action=action;
        dateOfAction=date;
        this.id=id;
    }
    void display(){
        System.out.println(arr[action]+" at "+dateOfAction+" by "+id);
    }
    static void displayActions(){
        for(int i=0;i<arr.length;i++){
            System.out.print((i+1)+". "+arr[i]+" ||  ");
        }
    }
}