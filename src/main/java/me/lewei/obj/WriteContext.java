package me.lewei.obj;

public class WriteContext {

	private String targetPath;
	private String fromName;
	private String toName;

	private String orgFullPath;
	private String newFullPath;

	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getOrgFullPath() {
		return orgFullPath;
	}

	public void setOrgFullPath(String orgFullPath) {
		this.orgFullPath = orgFullPath;
	}

	public String getNewFullPath() {
		return newFullPath;
	}

	public void setNewFullPath(String newFullPath) {
		this.newFullPath = newFullPath;
	}
	
	public void generateNewFullPath(){
		this.setNewFullPath(this.orgFullPath.replace(this.fromName, this.toName));
	}

}
