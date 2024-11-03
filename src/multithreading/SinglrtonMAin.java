package multithreading;

public class SinglrtonMAin {
	 public static void main(String[] args) {
	        Singleton singleton = Singleton.getInstance();
	        singleton.showMessage(); 
	    }
}
