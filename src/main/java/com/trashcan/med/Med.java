package com.trashcan.med;

public class Med {
    public int mid = 0;
    public String name = "";
    public String firm = "";
    public String substActiva = "";
    public String medType = "";
    public String madeFor = "";
    public String adm = "";
    public String description = "";
    public String imgLink = "";



    public Med(int mid, String name, String firm, String substActiva, String type, String madeFor, String adm, String description, String imgLink) {
        this.mid = mid;
        this.name = name;
        this.firm = firm;
        this.substActiva = substActiva;
        this.medType = type;
        this.madeFor = madeFor;
        this.adm = adm;
        this.description = description;
        this.imgLink = imgLink;
    }

    public Med() {
    }


    @Override
    public String toString() {
        return "Med{" +
                "name='" + name + '\'' +
                ", firm='" + firm + '\'' +
                ", substActiva='" + substActiva + '\'' +
                ", type='" + medType + '\'' +
                ", madeFor='" + madeFor + '\'' +
                ", adm='" + adm + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
