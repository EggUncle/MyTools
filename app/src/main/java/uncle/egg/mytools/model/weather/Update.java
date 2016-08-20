package uncle.egg.mytools.model.weather;

/**
 * Created by egguncle on 16.8.20.
 */
public class Update
{
    private String loc;

    private String utc;

    public void setLoc(String loc){
        this.loc = loc;
    }
    public String getLoc(){
        return this.loc;
    }
    public void setUtc(String utc){
        this.utc = utc;
    }
    public String getUtc(){
        return this.utc;
    }
}