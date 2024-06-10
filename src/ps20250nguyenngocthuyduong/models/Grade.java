package ps20250nguyenngocthuyduong.models;

public class Grade {
    private String testID;
    private String studentID;
    private Double tAGrade;
    private Double tinHocGrade;
    private Double gDTCGrade;

    public Grade() {
    }

    public Grade(String testID, String studentID, Double tAGrade, Double tinHocGrade, Double gDTCGrade) {
        this.testID = testID;
        this.studentID = studentID;
        this.tAGrade = tAGrade;
        this.tinHocGrade = tinHocGrade;
        this.gDTCGrade = gDTCGrade;
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public Double getTAGrade() {
        return tAGrade;
    }

    public void setTAGrade(Double tAGrade) {
        this.tAGrade = tAGrade;
    }

    public Double getTinHocGrade() {
        return tinHocGrade;
    }

    public void setTinHocGrade(Double tinHocGrade) {
        this.tinHocGrade = tinHocGrade;
    }

    public Double getGDTCGrade() {
        return gDTCGrade;
    }

    public void setGDTCGrade(Double gDTCGrade) {
        this.gDTCGrade = gDTCGrade;
    }
    
    
    
    public Double getDTB() {
        Double tA = 0.0, tinHoc = 0.0, gDTC = 0.0;
        int count = 3;
        
        if(getTAGrade() == null) count--;
        else tA = getTAGrade();
        
        if(getTinHocGrade()== null) count--;
        else tinHoc = getTinHocGrade();
        
        if(getGDTCGrade()== null) count--;
        else gDTC = getGDTCGrade();

        return (count != 0) ? (tA + tinHoc + gDTC) / count : 0;
    }
    
}
