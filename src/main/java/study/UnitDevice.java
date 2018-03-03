package study;

import java.util.ArrayList;

public class UnitDevice implements Comparable<UnitDevice>{
	private int number;
	private String name;
	private ArrayList<IpAddress> ipList=new ArrayList<IpAddress>();

	public UnitDevice(int number, String name, ArrayList<IpAddress> ipList) {
		super();
		this.number = number;
		this.name = name;
		this.ipList = ipList;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<IpAddress> getIpList() {
		return ipList;
	}

	public void setIpList(ArrayList<IpAddress> ipList) {
		this.ipList = ipList;
	}

	public int compareTo(UnitDevice other) {
		if(other.number==this.number)return 0;
		else if(other.number>this.number)return -1;
		else return 1;
	}



		
}
