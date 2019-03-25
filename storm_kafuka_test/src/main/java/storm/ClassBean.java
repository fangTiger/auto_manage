package storm;

/**
 * @Author:lww
 * @Date:18:18 2017/9/13
 */
public class ClassBean {
	private String name;
	private Integer classId;
	private SchoolBean schoolBean;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public SchoolBean getSchoolBean() {
		return schoolBean;
	}

	public void setSchoolBean(SchoolBean schoolBean) {
		this.schoolBean = schoolBean;
	}
}
