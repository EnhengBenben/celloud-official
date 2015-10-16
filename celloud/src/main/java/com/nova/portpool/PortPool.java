package com.nova.portpool;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.nova.constants.SparkPro;

/**
 * 端口池
 * 
 * @author lin
 */
public class PortPool {
	private static LinkedList<Integer> ports = new LinkedList<>();
	private static Map<String, Integer[]> pro_ports = new HashMap<String, Integer[]>();

	static {
		for (int i = SparkPro.START; i < SparkPro.START + SparkPro.NODES; i++) {
			ports.add(i);
		}
	}

	/**
	 * 绑定 projectId 和其所用到的 ports
	 * 
	 * @param projectId
	 * @param ports
	 */
	public synchronized static void proBindPorts(String projectId,
			Integer[] ports) {
		pro_ports.put(projectId, ports);
	}

	/**
	 * 获取端口
	 * 
	 * @return
	 */
	public synchronized static Integer getPort() {
		return ports.removeFirst();
	}

	/**
	 * 将 projectId 占用的端口放回端口池
	 * 
	 * @param projectId
	 */
    public synchronized static void setPort(String projectId) {
        if (pro_ports.containsKey(projectId)) {
            Integer[] ps = pro_ports.get(projectId);
            for (Integer p : ps) {
                ports.add(p);
            }
            pro_ports.remove(projectId);
        }
    }

	/**
	 * 端口池是否为空
	 * 
	 * @return
	 */
	public synchronized static boolean isEmpyt() {
		return ports.isEmpty();
	}

	/**
	 * 获取可用端口数量（也就等于集群可用节点数量）
	 * 
	 * @return
	 */
	public synchronized static int getSize() {
		return ports.size();
	}

}