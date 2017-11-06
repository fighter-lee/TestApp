package top.fighter_lee.testapp.info;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author fighter_lee
 * @date 2017/11/6
 */
public class TicketOpenData implements Parcelable {


    /**
     * timestamp : 1509960640
     * expect : 20171106069
     * time : 2017-11-06 17:30:40
     * name : 时时彩
     * code : cqssc
     * openCode : 8,9,7,1,9
     */

    private int timestamp;
    private String expect;
    private String time;
    private String name;
    private String code;
    private String openCode;

    protected TicketOpenData(Parcel in) {
        timestamp = in.readInt();
        expect = in.readString();
        time = in.readString();
        name = in.readString();
        code = in.readString();
        openCode = in.readString();
    }

    public static final Creator<TicketOpenData> CREATOR = new Creator<TicketOpenData>() {
        @Override
        public TicketOpenData createFromParcel(Parcel in) {
            return new TicketOpenData(in);
        }

        @Override
        public TicketOpenData[] newArray(int size) {
            return new TicketOpenData[size];
        }
    };

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOpenCode() {
        return openCode;
    }

    public void setOpenCode(String openCode) {
        this.openCode = openCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(timestamp);
        parcel.writeString(expect);
        parcel.writeString(time);
        parcel.writeString(name);
        parcel.writeString(code);
        parcel.writeString(openCode);
    }

}
