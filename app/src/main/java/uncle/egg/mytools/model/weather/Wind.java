package uncle.egg.mytools.model.weather;

/**
 * Created by egguncle on 16.8.20.
 */
public class Wind
{
    private String deg;

    private String dir;

    private String sc;

    private String spd;

    public void setDeg(String deg){
        this.deg = deg;
    }
    public String getDeg(){
        return this.deg;
    }
    public void setDir(String dir){
        this.dir = dir;
    }
    public String getDir(){
        return this.dir;
    }
    public void setSc(String sc){
        this.sc = sc;
    }
    public String getSc(){
        return this.sc;
    }
    public void setSpd(String spd){
        this.spd = spd;
    }
    public String getSpd(){
        return this.spd;
    }
}