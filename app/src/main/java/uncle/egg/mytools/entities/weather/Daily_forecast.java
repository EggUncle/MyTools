package uncle.egg.mytools.entities.weather;

/**
 * Created by egguncle on 16.8.20.
 */
public class Daily_forecast {
    private Astro astro;

    private Cond cond;

    private String date;

    private String hum;

    private String pcpn;

    private String pop;

    private String pres;

    private Tmp tmp;

    private String vis;

    private Wind wind;

    public void setAstro(Astro astro) {
        this.astro = astro;
    }

    public Astro getAstro() {
        return this.astro;
    }

    public void setCond(Cond cond) {
        this.cond = cond;
    }

    public Cond getCond() {
        return this.cond;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getHum() {
        return this.hum;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    public String getPcpn() {
        return this.pcpn;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPop() {
        return this.pop;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getPres() {
        return this.pres;
    }

    public void setTmp(Tmp tmp) {
        this.tmp = tmp;
    }

    public Tmp getTmp() {
        return this.tmp;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public String getVis() {
        return this.vis;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Wind getWind() {
        return this.wind;
    }
}
