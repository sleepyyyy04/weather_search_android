package zdj.hw;

public class WeatherCode {
    public WeatherCode() {
    }

    public String weatherName(int wcode) {
        if(wcode==4201) {
            return "Heavy Rain";
        }
        else if(wcode==4001) {
            return "Rain";
        }
        else if(wcode==4200) {
            return "Light Rain";
        }
        else if(wcode==6201) {
            return "Heavy Freezing Rain";
        }
        else if(wcode==6001) {
            return "Freezing Rain";
        }
        else if(wcode==6200) {
            return "Light Freezing Rain";
        }
        else if(wcode==6000) {
            return "Freezing Drizzle";
        }
        else if(wcode==4000) {
            return "Drizzle";
        }
        else if(wcode==7101) {
            return "Heavy Ice Pellets";
        }
        else if(wcode==7000) {
            return "Ice Pellets";
        }
        else if(wcode==7102) {
            return "Light Ice Pellets";
        }
        else if(wcode==5101) {
            return "Heavy Snow";
        }
        else if(wcode==5000) {
            return "Snow";
        }
        else if(wcode==5100) {
            return "Light Snow";
        }
        else if(wcode==5001) {
            return "Flurries";
        }
        else if(wcode==8000) {
            return "Thunderstorm";
        }
        else if(wcode==2100) {
            return "Light Fog";
        }
        else if(wcode==2000) {
            return "Fog";
        }
        else if(wcode==1001) {
            return "Cloudy";
        }
        else if(wcode==1102) {
            return "Mostly Cloudy";
        }
        else if(wcode==1101) {
            return "Partly Cloudy";
        }
        else if(wcode==1100) {
            return "Mostly Clear";
        }
        else if(wcode==1000) {
            return "Clear";
        }
        else if(wcode==3000) {
            return "Light Wind";
        }
        else if(wcode==3001) {
            return "Wind";
        }
        else if(wcode==3002) {
            return "Strong Wind";
        }
        else
            return  "Clear";
    }

    public int weatherUrl(int wcode) {
        if(wcode==4201) {
//            return 700161;
            return R.drawable.ic_rain_heavy;
        }
        else if(wcode==4001) {
            return R.drawable.ic_rain;
        }
        else if(wcode==4200) {
            return R.drawable.ic_rain_light;
        }
        else if(wcode==6201) {
            return R.drawable.ic_freezing_rain_heavy;
        }
        else if(wcode==6001) {
            return R.drawable.ic_freezing_rain;
        }
        else if(wcode==6200) {
            return R.drawable.ic_freezing_rain_light;
        }
        else if(wcode==6000) {
            return R.drawable.ic_freezing_drizzle;
        }
        else if(wcode==4000) {
            return R.drawable.ic_drizzle;
        }
        else if(wcode==7101) {
            return R.drawable.ic_ice_pellets_heavy;
        }
        else if(wcode==7000) {
            return R.drawable.ic_ice_pellets;
        }
        else if(wcode==7102) {
            return R.drawable.ic_ice_pellets_light;
        }
        else if(wcode==5101) {
            return R.drawable.ic_snow_heavy;
        }
        else if(wcode==5000) {
            return R.drawable.ic_snow;
        }
        else if(wcode==5100) {
            return R.drawable.ic_snow_light;
        }
        else if(wcode==5001) {
            return R.drawable.ic_flurries;
        }
        else if(wcode==8000) {
            return R.drawable.ic_tstorm;
        }
        else if(wcode==2100) {
            return R.drawable.ic_fog_light;
        }
        else if(wcode==2000) {
            return R.drawable.ic_fog;
        }
        else if(wcode==1001) {
            return R.drawable.ic_cloudy;
        }
        else if(wcode==1102) {
            return R.drawable.ic_mostly_cloudy;
        }
        else if(wcode==1101) {
            return R.drawable.ic_partly_cloudy_day;
        }
        else if(wcode==1100) {
            return R.drawable.ic_mostly_clear_day;
        }
        else if(wcode==1000) {
            return R.drawable.ic_clear_day;
        }
        else if(wcode==3000) {
            return R.drawable.ic_wind;
        }
        else if(wcode==3001) {
            return R.drawable.ic_light_wind;
        }
        else if(wcode==3002) {
            return R.drawable.ic_strong_wind;
        }
        else
            return R.drawable.ic_clear_day;
    }
}
