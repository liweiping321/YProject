package com.game.generate.proto;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jianpeng.zhang
 * @since 2017/3/7.
 */
@XmlRootElement(name = "module")
public class ModuleInfo {

	private String moduleDesc;

	private int minCode;

	private int maxCode;

	private String moduleName;

	private List<MsgBean> beanList;

	public ModuleInfo() {
	}

	public ModuleInfo(String moduleName) {
		this.moduleName = moduleName;
		beanList = new ArrayList<>();
	}

	public boolean inRange(int num) {
		return num >= minCode && num <= maxCode;
	}

	public int getMaxCode() {
		return maxCode;
	}

	@XmlAttribute(name = "maxCode")
	public void setMaxCode(int maxCode) {
		this.maxCode = maxCode;
	}

	public int getMinCode() {
		return minCode;
	}

	@XmlAttribute(name = "minCode")
	public void setMinCode(int minCode) {
		this.minCode = minCode;
	}

	public String getModuleDesc() {
		return moduleDesc;
	}

	@XmlAttribute(name = "desc")
	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	public String getModuleName() {
		return moduleName;
	}

	@XmlAttribute(name = "name")
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public List<MsgBean> getBeanList() {
		return beanList;
	}

	@XmlElement(name = "ProtoConfig")
	public void setBeanList(List<MsgBean> beanList) {
		this.beanList = beanList;
	}
}
