package ps20250nguyenngocthuyduong.models;

public class Student {
    private String studentID;
    private String studentName;
    private String email;
    private String phoneNumber;
    private String sex;
    private String address;

    public Student() {
    }

    public Student(String studentID, String studentName, String email, String phoneNumber, String sex, String address) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.address = address;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
