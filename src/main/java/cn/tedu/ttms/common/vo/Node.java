package cn.tedu.ttms.common.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

//@Alias("node")
public class Node implements Serializable{

	/**Node
	 * 1)为一个值对象(Value Object)
	 * 2)用于封装树节点信息
	 */
	private static final long serialVersionUID = 2307190311131483459L;
	private Integer id;
	private Integer parentId;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
