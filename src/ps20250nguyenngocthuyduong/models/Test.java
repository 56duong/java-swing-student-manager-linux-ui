package ps20250nguyenngocthuyduong.models;

public class Test {
    private String testID;
    private String testName;
    private String testDate;

    public Test() {
    }

    public Test(String testID, String testName, String testDate) {
        this.testID = testID;
        this.testName = testName;
        this.testDate = testDate;
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }
    
}
