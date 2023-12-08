package com.xian.freetype.word;

import android.util.Log;
import com.common.net.NetResult;
import java.lang.reflect.Array;

/* loaded from: classes.dex */
public class NdkFreeType {
    public static final int HORIZONTAL = 0;
    private static String TAG = "NdkFreeType";
    public static final int VERTICAL = 1;

    public static native void FT_Destroy_FreeType();

    public static native WordInfo FT_GET_Word_Info(int i, long j);

    public static native boolean FT_Init_FreeType(String str);

    static {
        System.loadLibrary("freetype");
    }

    public static int[] FT_GET_Word_Unicode(String str) {
        int[] iArr = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            iArr[i] = str.codePointAt(i);
            String str2 = TAG;
            Log.i(str2, "字符集： " + iArr[i]);
        }
        return iArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x0129 A[LOOP:7: B:34:0x00f5->B:49:0x0129, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[][] FT_GET_Word_Lattice(int r20, long r21) {
        /*
            Method dump skipped, instructions count: 410
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xian.freetype.word.NdkFreeType.FT_GET_Word_Lattice(int, long):byte[][]");
    }

    public static byte[][] FT_GET_Word_Lattice(String str, int i, int i2) {
        byte[][] bArr;
        if (i2 == 0) {
            int i3 = 0;
            for (int i4 = 0; i4 < str.length(); i4++) {
                i3 = str.codePointAt(i4) <= 128 ? i3 + (i / 2) : i3 + i;
            }
            bArr = (byte[][]) Array.newInstance(byte.class, i, i3);
        } else {
            boolean z = false;
            for (int i5 = 0; i5 < str.length(); i5++) {
                if (str.codePointAt(i5) > 128) {
                    z = true;
                }
            }
            bArr = (byte[][]) Array.newInstance(byte.class, str.length() * i, z ? i : i / 2);
        }
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < str.length(); i8++) {
            byte[][] FT_GET_Word_Lattice = FT_GET_Word_Lattice(i, str.codePointAt(i8));
            for (int i9 = 0; i9 < FT_GET_Word_Lattice.length; i9++) {
                for (int i10 = 0; i10 < FT_GET_Word_Lattice[0].length; i10++) {
                    bArr[i7 + i9][i6 + i10] = FT_GET_Word_Lattice[i9][i10];
                }
            }
            if (i2 == 0) {
                i6 += FT_GET_Word_Lattice[0].length;
            } else {
                i7 += i;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (byte[] bArr2 : bArr) {
            for (int i11 = 0; i11 < bArr[0].length; i11++) {
                if (bArr2[i11] == 1) {
                    sb.append("1");
                } else {
                    sb.append(NetResult.CODE_OK);
                }
            }
            sb.append("\n");
        }
        Log.i(TAG, "\n" + sb.toString());
        return bArr;
    }
}
