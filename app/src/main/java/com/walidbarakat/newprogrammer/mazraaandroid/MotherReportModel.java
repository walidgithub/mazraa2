package com.walidbarakat.newprogrammer.mazraaandroid;

/**
 * Created by PC on 19/02/2018.
 */

public class MotherReportModel {
    public String MotherNum = "";
    public String Dady = "";
    public String Partition = "";
    public String CreateDate = "";
    public String BabyNum = "";
    public String BirthDate = "";
    public String Gender = "";
    public String Tahsyn = "";
    public String TahsynDate = "";
    public String Medicin = "";
    public String MedicinDate = "";
    public String Notes = "";
    public String Users = "";

    public MotherReportModel(String motherNum, String dady, String partition, String createDate, String babyNum, String birthDate, String gender, String tahsyn, String tahsynDate, String medicin, String medicinDate, String notes, String users) {
        this.MotherNum = motherNum;
        this.Dady = dady;
        this.Partition = partition;
        this.CreateDate = createDate;
        this.BabyNum = babyNum;
        this.BirthDate = birthDate;
        this.Gender = gender;
        this.Tahsyn = tahsyn;
        this.TahsynDate = tahsynDate;
        this.Medicin = medicin;
        this.MedicinDate = medicinDate;
        this.Notes = notes;
        this.Users = users;
    }

}
