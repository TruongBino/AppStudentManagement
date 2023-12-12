package com.example.appstudentmanagement;

import java.util.Map;

public class Student {
    private String photoUrl;
    private String code;
    private String name;
    private String studentClass;
    private String dateOfBirth;
    private String address;
    private String phone;
    private String detail;
    private String scoreToan;
    private String scoreVan;
    private String scoreAnh;
    private String scoreVatLy;
    private String scoreLichSu;
    private String scoreDialy;
    private String scoreGDCD;
    private String scoreHoa;
    private String scoreSinh;
    private String scoreHanhKiem;
    private String scoreDTB;
    private String scoreHocLuc;

    public Student() {
        // Default constructor required for calls to DataSnapshot.getValue(Student.class)
    }

    public Student(String photoUrl, String code, String name, String studentClass,String dateOfBirth, String address, String phone, String detail,String scoreToan,
                   String scoreVan,String scoreAnh,String scoreVatLy,String scoreLichSu,String scoreDialy,String scoreGDCD,
                   String scoreHoa,String scoreSinh,String scoreHanhKiem,String scoreDTB,String scoreHocLuc) {
        this.photoUrl = photoUrl;
        this.code = code;
        this.name = name;
        this.studentClass = studentClass;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.detail = detail;
        this.scoreToan = scoreToan;
        this.scoreVan = scoreVan;
        this.scoreAnh = scoreAnh;
        this.scoreVatLy = scoreVatLy;
        this.scoreLichSu = scoreLichSu;
        this.scoreDialy = scoreDialy;
        this.scoreGDCD = scoreGDCD;
        this.scoreHoa = scoreHoa;
        this.scoreSinh = scoreSinh;
        this.scoreHanhKiem = scoreHanhKiem;
        this.scoreDTB = scoreDTB;
        this.scoreHocLuc = scoreHocLuc;



    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getScoreToan() {
        return scoreToan;
    }

    public void setScoreToan(String scoreToan) {
        this.scoreToan = scoreToan;
    }

    public String getScoreVan() {
        return scoreVan;
    }

    public void setScoreVan(String scoreVan) {
        this.scoreVan = scoreVan;
    }

    public String getScoreAnh() {
        return scoreAnh;
    }

    public void setScoreAnh(String scoreAnh) {
        this.scoreAnh = scoreAnh;
    }

    public String getScoreVatLy() {
        return scoreVatLy;
    }

    public void setScoreVatLy(String scoreVatLy) {
        this.scoreVatLy = scoreVatLy;
    }

    public String getScoreLichSu() {
        return scoreLichSu;
    }

    public void setScoreLichSu(String scoreLichSu) {
        this.scoreLichSu = scoreLichSu;
    }

    public String getScoreDialy() {
        return scoreDialy;
    }

    public void setScoreDialy(String scoreDialy) {
        this.scoreDialy = scoreDialy;
    }

    public String getScoreGDCD() {
        return scoreGDCD;
    }

    public void setScoreGDCD(String scoreGDCD) {
        this.scoreGDCD = scoreGDCD;
    }

    public String getScoreHoa() {
        return scoreHoa;
    }

    public void setScoreHoa(String scoreHoa) {
        this.scoreHoa = scoreHoa;
    }

    public String getScoreSinh() {
        return scoreSinh;
    }

    public void setScoreSinh(String scoreSinh) {
        this.scoreSinh = scoreSinh;
    }

    public String getScoreHanhKiem() {
        return scoreHanhKiem;
    }

    public void setScoreHanhKiem(String scoreHanhKiem) {
        this.scoreHanhKiem = scoreHanhKiem;
    }

    public String getScoreDTB() {
        return scoreDTB;
    }

    public void setScoreDTB(String scoreDTB) {
        this.scoreDTB = scoreDTB;
    }

    public String getScoreHocLuc() {
        return scoreHocLuc;
    }

    public void setScoreHocLuc(String scoreHocLuc) {
        this.scoreHocLuc = scoreHocLuc;
    }
}
