package com.walidbarakat.newprogrammer.mazraaandroid;

/**
 * Created by PC on 10/03/2018.
 */

public class BabyReportModel {
    public String BabyNum = "";
    public String BirthDate = "";
    public String Gender = "";
    public String Tomother = "";
    public String Tahsyn = "";
    public String TahsynDate = "";
    public String Medicin = "";
    public String MedicinDate = "";
    public String Notes = "";
    public String Users = "";

    public BabyReportModel(String babyNum, String birthDate, String gender, String tomother, String tahsyn, String tahsynDate, String medicin, String medicinDate, String notes, String users) {
        this.BabyNum = babyNum;
        this.BirthDate = birthDate;
        this.Gender = gender;
        this.Tomother = tomother;
        this.Tahsyn = tahsyn;
        this.TahsynDate = tahsynDate;
        this.Medicin = medicin;
        this.MedicinDate = medicinDate;
        this.Notes = notes;
        this.Users = users;
    }
}
