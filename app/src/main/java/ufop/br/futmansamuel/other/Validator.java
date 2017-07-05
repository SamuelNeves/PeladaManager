package ufop.br.futmansamuel.other;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ufop.br.futmansamuel.activities.MainActivity;

/**
 * @author Samuel Neves
 *         Created on 04/07/17.
 */

public class Validator {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private static  Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private static Matcher matcher;

    public static boolean validadeID(String id) {
        for (Players p : MainActivity.players) {
            if (p.getId().equals(id))
                return false;
        }
        return true;
    }

    public static boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validateFirstName(String s) {
        return s.length()>1;
    }

    public static boolean validateLastName(String s) {
        return s.length()>1;
    }

    public static boolean validateNickName(String s) {
        return s.length()>1;
    }

    public static boolean validateTelephone(String tel) {
        return !TextUtils.isEmpty(tel) && Patterns.PHONE.matcher(tel).matches();
    }


}
