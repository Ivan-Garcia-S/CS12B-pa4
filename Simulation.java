import java.io.*;
import java.util.Scanner;

public class Simulation {

	public static void main(String[] args) throws IOException{
		Scanner in = null;
		PrintWriter outrpt = null;
		PrintWriter outtrc = null;
		int jobs;
		Queue storage = new Queue();
		Queue backup = new Queue();
		Queue[] processors = null;
		int time;
		 
		if(args.length != 1){
			System.out.println("Usage: Simulation <input file> ");
			System.exit(1);
		}
		in = new Scanner(new File(args[0]));
		outrpt = new PrintWriter(new FileWriter(args[0] + ".rpt"));
		outtrc = new PrintWriter(new FileWriter(args[0] + ".trc"));
		jobs = in.nextInt();
		
		 
		while(in.hasNextLine()){
			in.nextLine();
			if(in.hasNextInt()) {
				Job j = new Job(in.nextInt(),in.nextInt());
			    storage.enqueue(j); 
			   
			}
		 }
		 outrpt.println("Report file: " + args[0] + ".rpt");
		 outrpt.println(jobs + " Jobs:");
		 outrpt.println(storage.toString());
		 outrpt.println();
		 outrpt.println("***********************************************************");
		 
		 outtrc.println("Trace file: " + args[0] + ".trc");
		 outtrc.println(jobs + " Jobs:");
		 outtrc.println(storage.toString());
		 outtrc.println();
		 
		 for(int n = 1; n < jobs; n++) {
			 processors = new Queue[n+1];
			 processors[0] = storage;
			 time = 0;
			 int totalWait = 0;
			 int maxWait = -1;
			 double avgWait = 0.00;
			 int nextArrivalTime;
			 int nextFinishedTime;
			 boolean done = true;
			 if(n == 1) {
				outtrc.println("*****************************");
				outtrc.println(n + " processor:");
				outtrc.println("*****************************");
			}
			else {
				outtrc.println("*****************************");
				outtrc.println(n + " processors:");
				outtrc.println("*****************************");
			}
			outtrc.println("time=" + time);
			outtrc.println("0: " + storage.toString());
			 
			for(int i = 1; i <=n;i++) {
				outtrc.println(i + ":");
				processors[i] = new Queue();
			}
			outtrc.println();
			Job first = (Job)storage.peek();
		
		    while(first.getFinish() == -1 || !done) {
		    	if((first.getFinish() != -1 && !done) || (storage.isEmpty() && !done)) {
					 int nextQueue = -1;
					 boolean found = false;
					 for(int i = 1; i <= n; i++) {
						 if(!processors[i].isEmpty() && !found) {
							 found = true;
							 Job j = (Job)processors[i].peek();
							 nextQueue = j.getFinish();
						 }
						 else if(!processors[i].isEmpty()) {
							 Job j = (Job)processors[i].peek();
							 if(j.getFinish() < nextQueue) {
								 nextQueue = j.getFinish();
							 }
						 }
					 }
					 time = nextQueue;
					 for(int i = 1; i <= n ; i++) {
						 if(!processors[i].isEmpty()) {
							 Job j = (Job)processors[i].peek();
							 if(j.getFinish() == time) {
								 Job dequeue = (Job)processors[i].dequeue();
								 storage.enqueue(dequeue);
								 if(!processors[i].isEmpty()) {
									 Job newHead = (Job)processors[i].peek();
									 newHead.computeFinishTime(time);
								 }
							 }
						 }
					 }
				 }
				 
				 else {
					 if(n == 3) {
						 time = time + 0;
					 }
				 Job nextJob = (Job)storage.peek();
				 nextArrivalTime = (nextJob.getArrival());
				 Job temp;
				 boolean foundFinishedJob = false; 
				 nextFinishedTime = nextArrivalTime;
				 //Checks if next finish time is before next arrival time
				 for(int i = 1; i <= n; i++) {
					 if(processors[i].length() !=0 && !foundFinishedJob) {
						 Job job = (Job)processors[i].peek();
						 if(job.getFinish() <= nextFinishedTime) {
							 nextFinishedTime = job.getFinish();
							 foundFinishedJob = true;
						 }
					 }
					 else if(processors[i].length() != 0) {
						 Job job = (Job)processors[i].peek();
						 if(job.getFinish() < nextFinishedTime)
						 nextFinishedTime = job.getFinish();			
					 }
				 }
				 //If there's a found job with a finished time equal to or less than next 
				 //arrival time, remove all the finished jobs 
				 if(foundFinishedJob) {
					 time = nextFinishedTime;
					 for(int i = 1; i <= n; i++) {
						 if(!processors[i].isEmpty()) {
							 Job job = (Job)processors[i].peek();
							 if(job.getFinish() == time) {
								 Job dequeue = (Job)processors[i].dequeue();
								 storage.enqueue(dequeue);
								 if(!processors[i].isEmpty()) {
									 Job newHead = (Job)processors[i].peek();
									 newHead.computeFinishTime(time);
								 }
							 }
						 }
					 }
				 }
				 //Else set current time to next arrival
				 else{
					 time = nextArrivalTime;
				 }
				 // Add front of storage to Queue if arrival time equals time along with 
				 //others of the same arrival time 	 	 
				 temp = (Job)storage.peek();	 
					 while(temp.getArrival() == time && storage.length() != 0) {
						 boolean queuedJob = false;
						 int shortestQLength = processors[1].length();
						 for(int i = 2; i <= n; i++) {		 
							 if(processors[i].length() < shortestQLength) {
								 shortestQLength = processors[i].length();
							 }
						 }
						 for(int i = 1; i <= n && !queuedJob; i++) {
							 if(processors[i].length() == shortestQLength) {
								 if(!storage.isEmpty()) {
								 temp = (Job)storage.dequeue();
								 if(processors[i].isEmpty()) {
								 temp.computeFinishTime(time);
								 processors[i].enqueue(temp);
								 backup.enqueue(temp);
								 }
								 else {
									 processors[i].enqueue(temp);
									 backup.enqueue(temp);
								 }
								 queuedJob = true;
								 }
							 }
						 }
						 if(!storage.isEmpty())
						 temp = (Job)storage.peek();
					 }
				 }		 
					 outtrc.println("time=" + time);
					 outtrc.print("0: "+ storage.toString() );  
					 outtrc.println();
					 for(int i = 1; i <= n; i++) {
						 outtrc.println(i + ": " + processors[i].toString());	
					 }
					 outtrc.println();
					 done = true;
					 if(!storage.isEmpty())
					 first = (Job)storage.peek();
					 
					 
					 for(int i = 1; i <=n; i++) {
						 if(!processors[i].isEmpty()) {
							 done = false;
						 }
					 }
					 
			 }
			 storage.dequeueAll(); 
			 while(!backup.isEmpty()) {
				 Job j = (Job)backup.dequeue();
				 if(j.getWaitTime()> maxWait) {
					maxWait = j.getWaitTime(); 
				 }
				 totalWait += j.getWaitTime();
				 j.resetFinishTime();
				 storage.enqueue(j);
			 }
			 avgWait += ((double)totalWait/jobs);
			 if(n == 1) {
				 outrpt.print("1 processor: " + "totalWait=" + totalWait + ", maxWait=" + 
						 maxWait + ", averageWait=");
				 outrpt.printf("%.2f\n",avgWait);
			 }
			 else {
				 outrpt.print(n + " processors: " + "totalWait=" + totalWait + ", maxWait=" + 
			             maxWait + ", averageWait=");
				 outrpt.printf("%.2f\n",avgWait);
			 }
		 }
		
		 in.close();
		 outrpt.close();
		 outtrc.close();
	}

}
