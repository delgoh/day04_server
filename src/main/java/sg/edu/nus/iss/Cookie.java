package sg.edu.nus.iss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cookie {
    List<String> cookies = null;
    
    public void readCookieFile(String fileName) throws IOException {
        cookies = new ArrayList<String>();

        File cookieFile = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(cookieFile));

        String cookieLine = "";
        while ((cookieLine = br.readLine()) != null) {
            cookies.add(cookieLine);
        }

        br.close();
    }
    public String getRandomCookie() {

        if (cookies == null) {
            return "There is no cookie";
        }

        Random rand = new Random();
        int randInt = rand.nextInt(this.cookies.size());
        return cookies.get(randInt);
    }
}
