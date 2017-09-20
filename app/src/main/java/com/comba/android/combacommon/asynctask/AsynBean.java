package com.comba.android.combacommon.asynctask;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenhailin on 2017/6/6.
 */

public class AsynBean implements Parcelable {
    private int resultCode = -1;
    private String resultMessage = "";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.resultCode);
        dest.writeString(this.resultMessage);
    }

    public AsynBean() {
    }

    protected AsynBean(Parcel in) {
        this.resultCode = in.readInt();
        this.resultMessage = in.readString();
    }

    public static final Parcelable.Creator<AsynBean> CREATOR = new Parcelable.Creator<AsynBean>() {
        @Override
        public AsynBean createFromParcel(Parcel source) {
            return new AsynBean(source);
        }

        @Override
        public AsynBean[] newArray(int size) {
            return new AsynBean[size];
        }
    };

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}