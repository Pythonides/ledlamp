package com.githang.statusbar;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class LightStatusBarCompat {
    private static final ILightStatusBar IMPL;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface ILightStatusBar {
        void setLightStatusBar(Window window, boolean z);
    }

    LightStatusBarCompat() {
    }

    static {
        if (MIUILightStatusBarImpl.isMe()) {
            if (Build.VERSION.SDK_INT >= 23) {
                IMPL = new MLightStatusBarImpl() { // from class: com.githang.statusbar.LightStatusBarCompat.1
                    private final ILightStatusBar DELEGATE = new MIUILightStatusBarImpl();

                    @Override // com.githang.statusbar.LightStatusBarCompat.MLightStatusBarImpl, com.githang.statusbar.LightStatusBarCompat.ILightStatusBar
                    public void setLightStatusBar(Window window, boolean z) {
                        super.setLightStatusBar(window, z);
                        this.DELEGATE.setLightStatusBar(window, z);
                    }
                };
            } else {
                IMPL = new MIUILightStatusBarImpl();
            }
        } else if (Build.VERSION.SDK_INT >= 23) {
            IMPL = new MLightStatusBarImpl();
        } else if (MeizuLightStatusBarImpl.isMe()) {
            IMPL = new MeizuLightStatusBarImpl();
        } else {
            IMPL = new ILightStatusBar() { // from class: com.githang.statusbar.LightStatusBarCompat.2
                @Override // com.githang.statusbar.LightStatusBarCompat.ILightStatusBar
                public void setLightStatusBar(Window window, boolean z) {
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setLightStatusBar(Window window, boolean z) {
        IMPL.setLightStatusBar(window, z);
    }

    /* loaded from: classes.dex */
    private static class MLightStatusBarImpl implements ILightStatusBar {
        private MLightStatusBarImpl() {
        }

        @Override // com.githang.statusbar.LightStatusBarCompat.ILightStatusBar
        public void setLightStatusBar(Window window, boolean z) {
            View decorView = window.getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            decorView.setSystemUiVisibility(z ? systemUiVisibility | 8192 : systemUiVisibility & (-8193));
        }
    }

    /* loaded from: classes.dex */
    private static class MIUILightStatusBarImpl implements ILightStatusBar {
        private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
        private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
        private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";

        private MIUILightStatusBarImpl() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:9:0x0030, code lost:
            if (r1.getProperty(com.githang.statusbar.LightStatusBarCompat.MIUILightStatusBarImpl.KEY_MIUI_INTERNAL_STORAGE) != null) goto L18;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        static boolean isMe() {
            /*
                r0 = 0
                r1 = 0
                java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L3c java.io.IOException -> L43
                java.io.File r3 = new java.io.File     // Catch: java.lang.Throwable -> L3c java.io.IOException -> L43
                java.io.File r4 = android.os.Environment.getRootDirectory()     // Catch: java.lang.Throwable -> L3c java.io.IOException -> L43
                java.lang.String r5 = "build.prop"
                r3.<init>(r4, r5)     // Catch: java.lang.Throwable -> L3c java.io.IOException -> L43
                r2.<init>(r3)     // Catch: java.lang.Throwable -> L3c java.io.IOException -> L43
                java.util.Properties r1 = new java.util.Properties     // Catch: java.lang.Throwable -> L37 java.io.IOException -> L3a
                r1.<init>()     // Catch: java.lang.Throwable -> L37 java.io.IOException -> L3a
                r1.load(r2)     // Catch: java.lang.Throwable -> L37 java.io.IOException -> L3a
                java.lang.String r3 = "ro.miui.ui.version.code"
                java.lang.String r3 = r1.getProperty(r3)     // Catch: java.lang.Throwable -> L37 java.io.IOException -> L3a
                if (r3 != 0) goto L32
                java.lang.String r3 = "ro.miui.ui.version.name"
                java.lang.String r3 = r1.getProperty(r3)     // Catch: java.lang.Throwable -> L37 java.io.IOException -> L3a
                if (r3 != 0) goto L32
                java.lang.String r3 = "ro.miui.internal.storage"
                java.lang.String r1 = r1.getProperty(r3)     // Catch: java.lang.Throwable -> L37 java.io.IOException -> L3a
                if (r1 == 0) goto L33
            L32:
                r0 = 1
            L33:
                r2.close()     // Catch: java.io.IOException -> L36
            L36:
                return r0
            L37:
                r0 = move-exception
                r1 = r2
                goto L3d
            L3a:
                r1 = r2
                goto L44
            L3c:
                r0 = move-exception
            L3d:
                if (r1 == 0) goto L42
                r1.close()     // Catch: java.io.IOException -> L42
            L42:
                throw r0
            L43:
            L44:
                if (r1 == 0) goto L49
                r1.close()     // Catch: java.io.IOException -> L49
            L49:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.githang.statusbar.LightStatusBarCompat.MIUILightStatusBarImpl.isMe():boolean");
        }

        @Override // com.githang.statusbar.LightStatusBarCompat.ILightStatusBar
        public void setLightStatusBar(Window window, boolean z) {
            Class<?> cls = window.getClass();
            try {
                Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                int i = cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls2);
                Method method = cls.getMethod("setExtraFlags", Integer.TYPE, Integer.TYPE);
                Object[] objArr = new Object[2];
                objArr[0] = Integer.valueOf(z ? i : 0);
                objArr[1] = Integer.valueOf(i);
                method.invoke(window, objArr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* loaded from: classes.dex */
    private static class MeizuLightStatusBarImpl implements ILightStatusBar {
        private MeizuLightStatusBarImpl() {
        }

        static boolean isMe() {
            return Build.DISPLAY.startsWith("Flyme");
        }

        @Override // com.githang.statusbar.LightStatusBarCompat.ILightStatusBar
        public void setLightStatusBar(Window window, boolean z) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            try {
                Field declaredField = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field declaredField2 = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                declaredField.setAccessible(true);
                declaredField2.setAccessible(true);
                int i = declaredField.getInt(null);
                int i2 = declaredField2.getInt(attributes);
                declaredField2.setInt(attributes, z ? i2 | i : (i ^ (-1)) & i2);
                window.setAttributes(attributes);
                declaredField.setAccessible(false);
                declaredField2.setAccessible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
