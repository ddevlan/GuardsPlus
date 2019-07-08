package me.ohvalsgod.guardsplus;

import com.besaba.revonline.pastebinapi.Pastebin;
import com.besaba.revonline.pastebinapi.impl.factory.PastebinFactory;
import com.besaba.revonline.pastebinapi.paste.Paste;
import com.besaba.revonline.pastebinapi.paste.PasteBuilder;
import com.besaba.revonline.pastebinapi.paste.PasteExpire;
import com.besaba.revonline.pastebinapi.paste.PasteVisiblity;
import com.besaba.revonline.pastebinapi.response.Response;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    private static final String DEV_KEY = "dev_key";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static PastebinFactory factory = new PastebinFactory();
    private static Pastebin pastebin = factory.createPastebin(DEV_KEY);

    public static void main(String[] args) {
        Response<String> loginResponse = pastebin.login(USERNAME, PASSWORD);

        if (loginResponse.hasError()) {
            System.out.println("Could not log you in, try again later.");
            return;
        }

        String userKey = loginResponse.get();

        PasteBuilder builder = factory.createPaste();
        Paste paste = builder
                .setTitle("Test paste generated bin at " + new SimpleDateFormat("dd-MM-yyyy hh:mm:ss z").format(new Date(System.currentTimeMillis())))
                .setRaw("this is a test paste bin sent from the pastebin-java-api")
                .setMachineFriendlyLanguage("text")
                .setVisiblity(PasteVisiblity.Public)
                .setExpire(PasteExpire.TenMinutes)
                .build();

        Response<String> response = pastebin.post(paste, userKey);

        if (response.hasError()) {
            System.out.println("There was an error posting your pastebin: " + response.getError());
            return;
        }

        System.out.println("Your pastebin link has been posted, you can grab the contents from: " + response.get());
    }

}
