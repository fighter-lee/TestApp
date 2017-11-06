package top.fighter_lee.testapp.info;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * @author fighter_lee
 * @date 2017/11/6
 */
public class TicketType implements Comparable<TicketType>, Parcelable {


    /**
     * area :
     * code : dlt
     * descr : 超级大乐透
     * high : false
     * hots : false
     * issuer : 体彩
     * notes : 每周一、三、六的20:30开奖
     * series :
     * times : 1
     */

    private String area;
    private String code;
    private String descr;
    private String high;
    private String hots;
    private String issuer;
    private String notes;
    private String series;
    private String times;

    protected TicketType(Parcel in) {
        area = in.readString();
        code = in.readString();
        descr = in.readString();
        high = in.readString();
        hots = in.readString();
        issuer = in.readString();
        notes = in.readString();
        series = in.readString();
        times = in.readString();
    }

    public static final Creator<TicketType> CREATOR = new Creator<TicketType>() {
        @Override
        public TicketType createFromParcel(Parcel in) {
            return new TicketType(in);
        }

        @Override
        public TicketType[] newArray(int size) {
            return new TicketType[size];
        }
    };

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getHots() {
        return hots;
    }

    public void setHots(String hots) {
        this.hots = hots;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    @Override
    public int compareTo(@NonNull TicketType ticketType) {
        return area.compareTo(ticketType.area);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(area);
        parcel.writeString(code);
        parcel.writeString(descr);
        parcel.writeString(high);
        parcel.writeString(hots);
        parcel.writeString(issuer);
        parcel.writeString(notes);
        parcel.writeString(series);
        parcel.writeString(times);
    }
}
