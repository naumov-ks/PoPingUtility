package study;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Callable;

public class ThreadPing implements Callable<UnitDevice> {
	private ListUnitDevices list;

	public ThreadPing(ListUnitDevices list) {
		this.list = list;
	}

	public UnitDevice call() throws Exception {
		UnitDevice unit = list.getUnitDevice();
		if (unit == null) {
			System.out.println("����������� ����� � list");
			return null;
		}
		System.out.println(Thread.currentThread().getName() + " ������� �� ����� " + unit.getName());
		ArrayList<IpAddress> ipList = unit.getIpList();
		for (int i = 0; i < ipList.size(); i++) {
			IpAddress ip = ipList.get(i);
			poPing(ip);
		}
		unit.setIpList(ipList);
		System.out.println(Thread.currentThread().getName() + " �������� ��������� " + unit.getName());
		return unit;
	}

	// ������� � ���������� ���������
	public void poPing(IpAddress ipPing) throws IOException {
		String ip = ipPing.getAddress();
		int answer = 0;
		System.out.println(Thread.currentThread().getName() +" ����� ��������� " + ip);
		if (ip != "") {
			InetAddress host = InetAddress.getByName(ip);
			boolean ping = host.isReachable(1000);
			if (!ping) {
				int i = 0;
				while (i < 1) {
					if (host.isReachable(1000)) {
						answer++;
					}
					i++;
				}
			} else
				answer++;

			if (answer > 0) {
				ipPing.setAnswer(1);
			} else
				ipPing.setAnswer(0);
		} else {
			System.out.println("�������� ������ ������!");
		return;
		}
		System.out.println(Thread.currentThread().getName() + " �������� ��������� " + ip+"� ����������� answer "+ answer);
		return;
	}

}
