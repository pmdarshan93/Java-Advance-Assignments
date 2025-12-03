package assignment006;

public class SmartHome {
	
	private String masterKey="1234";
	private Device currentDevice;

	
	
	boolean authentication(String key) {
		class AccessValidator{
			boolean validate(String key) {
			return SmartHome.this.masterKey.equals(key);
			}
		}
		return new AccessValidator().validate(key);
	}
	
	void switchDevice(Device d) {
		this.currentDevice=d;
		System.out.println("Switched to Device : "+d.name);
	}
	
	Device getCurrentDevice() {
		return currentDevice;
	}
	
	 void performTemporaryAction() {
		 Operations opr=new Operations(){
			 public void execute() {
				 System.out.println("Performing some temporary action in smart Home");
			 }
		 };
		 opr.execute();
	 }
	
	 class Device{
		 String name;
		 boolean isOn;
	 
		 Device(String name,boolean isOn){
			 this.name=name;
			 this.isOn=isOn;
		 }
		 
		 void turnOn() {
			 this.isOn=true;
			 System.out.println(name+" is ON now");
		 }
		 
		 void turnOff() {
			 this.isOn=false;
			 System.out.println(name+" is OFF now");
		 }
		 
		 void status() {
			 System.out.println(name+" is "+(isOn?"ON":"OFF")+" now");
		 }
	 }
	 
	 static class Settings{
		 	static int maxDevicesAllowed=10 ;
			static String systemVersion="1.0";
			
			void showSettings() {
				System.out.println("Max Devices : "+maxDevicesAllowed+"\n System Version : "+systemVersion);
			}
			
	 }
	 
public static void main(String[]main) {
	SmartHome myHome=new SmartHome();
	String key="213";
	if(myHome.authentication(key)) {
		System.out.println("Access Permitted");
	}
	else {
		System.out.println("Access Denied");
	}
	key="1234";
	if(myHome.authentication(key)) {
		System.out.println("Access Permitted");
	}
	else {
		System.out.println("Access Denied");
	}
	

	SmartHome.Device fan = myHome.new Device("Fan", false);
	SmartHome.Device light = myHome.new Device("Light", false);
	
	myHome.switchDevice(fan);
	fan.status();
	fan.turnOn();
	fan.turnOff();
	myHome.switchDevice(light);
	light.status();
	light.turnOn();
	light.turnOff();
	myHome.performTemporaryAction();
	
	SmartHome.Settings settings=new SmartHome.Settings();
	settings.showSettings();
}
}
