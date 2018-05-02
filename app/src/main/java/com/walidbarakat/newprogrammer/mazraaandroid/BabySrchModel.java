package com.walidbarakat.newprogrammer.mazraaandroid;

/**
 * Created by PC on 09/02/2018.
 */

public class BabySrchModel {
    public String Code = "";
    public String Tahsyn = "";
    public String TahsynDate = "";
    public String Medicin = "";
    public String MedicinDate = "";
    public String Notes = "";

    public BabySrchModel(String code, String tahsyn, String tahsynDate, String medicin, String medicinDate, String notes) {
        this.Code = code;
        this.Tahsyn = tahsyn;
        this.TahsynDate = tahsynDate;
        this.Medicin = medicin;
        this.MedicinDate = medicinDate;
        this.Notes = notes;
    }

}
