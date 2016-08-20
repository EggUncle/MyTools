package uncle.egg.mytools.model.weather;

/**
 * Created by egguncle on 16.8.20.
 */
public class Tmp
{
    private String max;

    private String min;

    public void setMax(String max){
        this.max = max;
    }
    public String getMax(){
        return this.max;
    }
    public void setMin(String min){
        this.min = min;
    }
    public String getMin(){
        return this.min;
    }
}