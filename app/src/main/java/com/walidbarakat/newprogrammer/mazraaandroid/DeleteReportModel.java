package com.walidbarakat.newprogrammer.mazraaandroid;

/**
 * Created by PC on 12/03/2018.
 */

public class DeleteReportModel {
    public String MotherNum="";
    public String BabyNum="";
    public String Gender="";
    public String ToMother2="";
    public String Reason="";
    public String DeleteDate="";
    public String Users="";

    public DeleteReportModel(String motherNum, String babyNum, String gender, String toMother2, String reason, String deleteDate, String users) {
        this.MotherNum = motherNum;
        this.BabyNum = babyNum;
        this.Gender = gender;
        this.ToMother2 = toMother2;
        this.Reason = reason;
        this. DeleteDate = deleteDate;
        this.Users = users;
    }


}
