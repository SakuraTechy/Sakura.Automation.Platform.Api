package com.sakura.system.base;

import lombok.Data;

import java.util.Map;

/**
 * <br>对应于XML文件中的step元素</br>
 *
 * @version 1.0
 * @since   1.0
 */
@Data
public class TestStep {


    private String id;

    private String name;

    private boolean cancel;
	/**
	 * 当前步骤的行为，调用StepAction中的方法
	 * <h1>action="keybg"</h1>
	 **/
	private String action;

    /**
     * Action行为对应的元素定位，包含索引，属性名=属性值[索引]
     * 如果可以确定唯一要获取的元素，则可以省略[索引]
     * <h1>locator="resource-id=com.giveu.corder:id/tv_login"</h1>
     * <h1>locator="id=su[0]"</h1>
     **/
    private String locator;
    
	/**
	 * Action行为对应所需要传递的参数值 
	 * <h1>value="5000"</h1>
	 **/
    private String value;
    
    private String element; 
    /**
     * Action行为对应的操作备注
     * <h1>desc="输入客户家庭联系人姓名"</h1>
     **/
    private String desc;
    
	/**
	 * 行为的细节描述，其格式与css中的样式格式一致，比如<br/>
	 * 在要做滑动操作时，细节描述如下，duration代表滑动时间，num代表滑动次数，amp代表滑动幅度
	 * <h1>details="duration:500;num:2;amp:1"</h1>
	 * 具体不同的操作的文档会在项目稳定后整理出来
	 **/
	private Map<String,String> details;
	
	 /**
     * 标记用例的特殊状态
     * <h1>state="true"</h1>
     **/
    private String state;
    
    /**
     * 标记用例是否跳过
     * <h1>skip="true"</h1>
     **/
    private String skip;
    
	/**
	 * 模拟Windows键盘按键操作的键值，单个按键
	 * <h1>key="F5"</h1>
	 **/
	private String key;
	
	/**
	 * 模拟Windows键盘按键操作的键值，组合按键
	 *<h1>key="CONTROL" keys="TAB"</h1>
	 **/
	private String keys;
	
	/**
	 * 检测结果的预期值
	 *<h1>expect="百度"</h1>
	 **/
    private String expect;
    
	/**
	 * 检测结果不匹配时的提示信息，用于记录到测试报告中
	 */
	private String message;
    
	/**
	 * 失败用例的截图名称
	 *<h1>casename="case1"</h1>
	 **/
	private String casename;

    /**
     * 关联的用例ID
     */
    private String caseId;
    
	/**
	 * 调用后台接口的URL地址
	 *<h1>url="https://travel.api.szjqb.net/api/Web/Order/completes"</h1>
	 **/
    private String url;

    /**
     * 调用后台接口所需传递的参数类型名称
     *<h1>headers="tokenId=oaGjrJZrY7lJaXgkOQJ7ai8L5Sra+oOA4Y/XNiryOoQ="</h1>
     **/
    private String headers;
    
	/**
	 * 调用后台接口所需传递的参数类型名称
	 *<h1>body="order_sn"</h1>
	 **/
    private String body;
    
    /**
     * 调用数据库所需的信息
     *<h1>datatype="MySql"</h1>
     *<h1>dataenviron="测试环境"</h1>
     *<h1>database="dbtest01"</h1>
     *<h1>sql="SELECT STR1 from m_getmsg_water where phonno ='17378403911' ORDER BY create_date DESC LIMIT 1"</h1>
     **/
    private String datatype;
    private String dataenviron;
    private String database;
    private String sql;
    
    private String device;
    private String shell;
    
    /**
     * Step操作步骤序号
     *<h1>index="5"</h1>
     **/
    private int index;
    
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
 
    public String getLocator() {
        return locator;
    }

    public void setLocator(String locator) {
        this.locator = locator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }
    
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
	public Map<String,String> getDetails() {
		return details;
	}

	public void setDetails(Map<String,String> details) {
		this.details = details;
	}
	
	public String getState() {
        return state;
    }

    public void setSkip(String skip) {
        this.skip = skip;
    }
    
    public String getSkip() {
        return skip;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    
    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }
    
    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }
    
    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }
    
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }
    
    public String getDataenviron() {
        return dataenviron;
    }

    public void setDataenviron(String dataenviron) {
        this.dataenviron = dataenviron;
    }
    
    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
    
    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
    
    public String getShell() {
        return shell;
    }

    public void setShell(String shell) {
        this.shell = shell;
    }
    
	@Override
	public String toString() {
		return "TestStep ["
		    + "action=" + action + ", locator=" + locator + ", value=" + value + ", desc=" + desc + ", details=" + details + ", key=" + key + ", keys=" + keys + ", expect=" + expect + ", message=" + message + ", "
		    + "url=" + url +", body=" + body + ", "
		    + "datatype=" + datatype + ", dataenviron=" + dataenviron + ", database=" + database + ", sql=" + sql + ""
		    + "]";
	}
}