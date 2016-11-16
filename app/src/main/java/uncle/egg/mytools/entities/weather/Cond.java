package uncle.egg.mytools.entities.weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by egguncle on 16.8.20.
 */
public class Cond
{
    @SerializedName("code")
    private String code_d;

    private String code_n;

    @SerializedName("txt")
    private String txt_d;

    private String txt_n;

    public void setCode_d(String code_d){
        this.code_d = code_d;
    }
    public String getCode_d(){
        return this.code_d;
    }
    public void setCode_n(String code_n){
        this.code_n = code_n;
    }
    public String getCode_n(){
        return this.code_n;
    }
    public void setTxt_d(String txt_d){
        this.txt_d = txt_d;
    }
    public String getTxt_d(){
        return this.txt_d;
    }
    public void setTxt_n(String txt_n){
        this.txt_n = txt_n;
    }
    public String getTxt_n(){
        return this.txt_n;
    }
}

