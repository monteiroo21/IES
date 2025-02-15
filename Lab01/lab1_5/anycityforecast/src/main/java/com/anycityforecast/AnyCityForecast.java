package com.anycityforecast;

import java.util.TimerTask;

import com.ipmaapiclient.WeatherStarter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Toolkit;
import java.util.Timer;

public class AnyCityForecast {
    Toolkit toolkit;

    Timer timer;

    private static List<String> cityCodes = new ArrayList<>();

    public AnyCityForecast() {
        toolkit = Toolkit.getDefaultToolkit();
        timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), 0, 20 * 1000);
    }

    class RemindTask extends TimerTask {
        Random random  = new Random();
        public void run() {
            long time = System.currentTimeMillis();
            if (time - scheduledExecutionTime() > 5) {
                return;
            }

            String code = getRandomCityCode();

            WeatherStarter.main(new String[]{code});
        }

        private String getRandomCityCode() {
            int idx = random.nextInt(cityCodes.size());
            return cityCodes.get(idx);
        }
    }

    public static void main(String args[]) {
        cityCodes.add("1010500");
        cityCodes.add("1020500");
        cityCodes.add("1030300");
        cityCodes.add("1030800");
        cityCodes.add("1040200");
        cityCodes.add("1050200");
        cityCodes.add("1060300");
        cityCodes.add("1070500");
        cityCodes.add("1080500");
        cityCodes.add("1080800");
        cityCodes.add("1081100");
        cityCodes.add("1081505");
        cityCodes.add("1090700");
        cityCodes.add("1090821");
        cityCodes.add("1100900");
        cityCodes.add("1110600");
        cityCodes.add("1121400");
        cityCodes.add("1131200");
        cityCodes.add("1141600");
        cityCodes.add("1151200");
        cityCodes.add("1151300");
        cityCodes.add("1160900");
        cityCodes.add("1171400");
        cityCodes.add("1182300");
        cityCodes.add("2310300");
        cityCodes.add("2320100");
        cityCodes.add("3410100");
        cityCodes.add("3420300");
        cityCodes.add("3430100");
        cityCodes.add("3440100");
        cityCodes.add("3450200");
        cityCodes.add("3460200");
        cityCodes.add("3470100");
        cityCodes.add("3480200");
        cityCodes.add("3490100");
        new AnyCityForecast();
    }
}
