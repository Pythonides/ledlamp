package com.common.uitl;

import androidx.exifinterface.media.ExifInterface;
import com.common.net.NetResult;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class IdcardValidator {
    private String[][] codeAndCity = {new String[]{"11", "北京"}, new String[]{"12", "天津"}, new String[]{"13", "河北"}, new String[]{"14", "山西"}, new String[]{"15", "内蒙古"}, new String[]{"21", "辽宁"}, new String[]{"22", "吉林"}, new String[]{"23", "黑龙江"}, new String[]{"31", "上海"}, new String[]{"32", "江苏"}, new String[]{"33", "浙江"}, new String[]{"34", "安徽"}, new String[]{"35", "福建"}, new String[]{"36", "江西"}, new String[]{"37", "山东"}, new String[]{"41", "河南"}, new String[]{"42", "湖北"}, new String[]{"43", "湖南"}, new String[]{"44", "广东"}, new String[]{"45", "广西"}, new String[]{"46", "海南"}, new String[]{"50", "重庆"}, new String[]{"51", "四川"}, new String[]{"52", "贵州"}, new String[]{"53", "云南"}, new String[]{"54", "西藏"}, new String[]{"61", "陕西"}, new String[]{"62", "甘肃"}, new String[]{"63", "青海"}, new String[]{"64", "宁夏"}, new String[]{"65", "新疆"}, new String[]{"71", "台湾"}, new String[]{"81", "香港"}, new String[]{"82", "澳门"}, new String[]{"91", "国外"}};
    private String[] cityCode = {"11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81", "82", "91"};
    private int[] power = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private String[] verifyCode = {"1", NetResult.CODE_OK, "X", "9", "8", "7", "6", "5", "4", ExifInterface.GPS_MEASUREMENT_3D, ExifInterface.GPS_MEASUREMENT_2D};

    public boolean isValidatedAllIdcard(String str) {
        if (str.length() == 15) {
            str = convertIdcarBy15bit(str);
        }
        return isValidate18Idcard(str);
    }

    private boolean isValidate18Idcard(String str) {
        String checkCodeBySum;
        if (str.length() != 18) {
            return false;
        }
        String substring = str.substring(0, 17);
        String substring2 = str.substring(17, 18);
        if (isDigital(substring)) {
            char[] charArray = substring.toCharArray();
            if (charArray != null) {
                int[] iArr = new int[substring.length()];
                int powerSum = getPowerSum(converCharToInt(charArray));
                return (powerSum == 0 || (checkCodeBySum = getCheckCodeBySum(powerSum)) == null || !substring2.equalsIgnoreCase(checkCodeBySum)) ? false : true;
            }
            return true;
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00ac, code lost:
        if (r13 <= 30) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ae, code lost:
        r13 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00cc, code lost:
        if (r13 <= 31) goto L38;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isValidate15Idcard(java.lang.String r13) {
        /*
            r12 = this;
            int r0 = r13.length()
            r1 = 0
            r2 = 15
            if (r0 == r2) goto La
            return r1
        La:
            boolean r0 = r12.isDigital(r13)
            if (r0 == 0) goto Ld3
            r0 = 2
            java.lang.String r2 = r13.substring(r1, r0)
            r3 = 6
            r4 = 12
            java.lang.String r5 = r13.substring(r3, r4)
            r6 = 8
            java.lang.String r3 = r13.substring(r3, r6)
            java.lang.String r3 = r3.trim()
            int r3 = java.lang.Integer.parseInt(r3)
            r7 = 10
            java.lang.String r6 = r13.substring(r6, r7)
            java.lang.String r6 = r6.trim()
            int r6 = java.lang.Integer.parseInt(r6)
            java.lang.String r13 = r13.substring(r7, r4)
            java.lang.String r13 = r13.trim()
            int r13 = java.lang.Integer.parseInt(r13)
            java.lang.String[] r7 = r12.cityCode
            int r8 = r7.length
            r9 = 0
        L48:
            r10 = 1
            if (r9 >= r8) goto L58
            r11 = r7[r9]
            boolean r11 = r11.equals(r2)
            if (r11 == 0) goto L55
            r2 = 1
            goto L59
        L55:
            int r9 = r9 + 1
            goto L48
        L58:
            r2 = 0
        L59:
            if (r2 != 0) goto L5c
            return r1
        L5c:
            r2 = 0
            java.text.SimpleDateFormat r7 = new java.text.SimpleDateFormat     // Catch: java.text.ParseException -> L69
            java.lang.String r8 = "yyMMdd"
            r7.<init>(r8)     // Catch: java.text.ParseException -> L69
            java.util.Date r2 = r7.parse(r5)     // Catch: java.text.ParseException -> L69
            goto L6d
        L69:
            r5 = move-exception
            r5.printStackTrace()
        L6d:
            if (r2 == 0) goto Ld3
            java.util.Date r5 = new java.util.Date
            r5.<init>()
            boolean r5 = r5.before(r2)
            if (r5 == 0) goto L7b
            goto Ld3
        L7b:
            java.util.GregorianCalendar r5 = new java.util.GregorianCalendar
            r5.<init>()
            int r7 = r5.get(r10)
            java.lang.String r7 = java.lang.String.valueOf(r7)
            java.lang.String r0 = r7.substring(r0)
            java.lang.String r0 = r0.trim()
            int r0 = java.lang.Integer.parseInt(r0)
            r7 = 50
            if (r3 >= r7) goto L9b
            if (r3 <= r0) goto L9b
            return r1
        L9b:
            if (r6 < r10) goto Ld3
            if (r6 <= r4) goto La0
            goto Ld3
        La0:
            r5.setTime(r2)
            switch(r6) {
                case 1: goto Lc8;
                case 2: goto Lb0;
                case 3: goto Lc8;
                case 4: goto La8;
                case 5: goto Lc8;
                case 6: goto La8;
                case 7: goto Lc8;
                case 8: goto Lc8;
                case 9: goto La8;
                case 10: goto Lc8;
                case 11: goto La8;
                case 12: goto Lc8;
                default: goto La6;
            }
        La6:
            r13 = 0
            goto Lcf
        La8:
            if (r13 < r10) goto La6
            r0 = 30
            if (r13 > r0) goto La6
        Lae:
            r13 = 1
            goto Lcf
        Lb0:
            int r0 = r5.get(r10)
            boolean r0 = r5.isLeapYear(r0)
            if (r0 == 0) goto Lc1
            if (r13 < r10) goto La6
            r0 = 29
            if (r13 > r0) goto La6
            goto Lae
        Lc1:
            if (r13 < r10) goto La6
            r0 = 28
            if (r13 > r0) goto La6
            goto Lae
        Lc8:
            if (r13 < r10) goto La6
            r0 = 31
            if (r13 > r0) goto La6
            goto Lae
        Lcf:
            if (r13 != 0) goto Ld2
            return r1
        Ld2:
            return r10
        Ld3:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.common.uitl.IdcardValidator.isValidate15Idcard(java.lang.String):boolean");
    }

    private String convertIdcarBy15bit(String str) {
        Date date;
        if (str.length() == 15 && isDigital(str)) {
            try {
                date = new SimpleDateFormat("yyMMdd").parse(str.substring(6, 12));
            } catch (ParseException e) {
                e.printStackTrace();
                date = null;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String str2 = str.substring(0, 6) + String.valueOf(calendar.get(1)) + str.substring(8);
            char[] charArray = str2.toCharArray();
            if (charArray != null) {
                int[] iArr = new int[str2.length()];
                String checkCodeBySum = getCheckCodeBySum(getPowerSum(converCharToInt(charArray)));
                if (checkCodeBySum == null) {
                    return null;
                }
                return str2 + checkCodeBySum;
            }
            return str2;
        }
        return null;
    }

    private boolean isIdcard(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        return Pattern.matches("(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)", str);
    }

    public boolean is15Idcard(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        return Pattern.matches("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$", str);
    }

    public boolean is18Idcard(String str) {
        return Pattern.matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$", str);
    }

    private boolean isDigital(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        return str.matches("^[0-9]*$");
    }

    private int getPowerSum(int[] iArr) {
        if (this.power.length != iArr.length) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            int i3 = 0;
            while (true) {
                int[] iArr2 = this.power;
                if (i3 < iArr2.length) {
                    if (i2 == i3) {
                        i += iArr[i2] * iArr2[i3];
                    }
                    i3++;
                }
            }
        }
        return i;
    }

    private String getCheckCodeBySum(int i) {
        switch (i % 11) {
            case 0:
                return "1";
            case 1:
                return NetResult.CODE_OK;
            case 2:
                return "x";
            case 3:
                return "9";
            case 4:
                return "8";
            case 5:
                return "7";
            case 6:
                return "6";
            case 7:
                return "5";
            case 8:
                return "4";
            case 9:
                return ExifInterface.GPS_MEASUREMENT_3D;
            case 10:
                return ExifInterface.GPS_MEASUREMENT_2D;
            default:
                return null;
        }
    }

    private int[] converCharToInt(char[] cArr) throws NumberFormatException {
        int[] iArr = new int[cArr.length];
        int length = cArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            iArr[i2] = Integer.parseInt(String.valueOf(cArr[i]).trim());
            i++;
            i2++;
        }
        return iArr;
    }
}
