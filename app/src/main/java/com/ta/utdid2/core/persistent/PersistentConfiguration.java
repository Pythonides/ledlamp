package com.ta.utdid2.core.persistent;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import com.ta.utdid2.android.utils.StringUtils;
import com.ta.utdid2.core.persistent.MySharedPreferences;
import java.io.File;
import java.util.Map;

/* loaded from: classes.dex */
public class PersistentConfiguration {
    private static final String KEY_TIMESTAMP = "t";
    private static final String KEY_TIMESTAMP2 = "t2";
    private boolean mCanRead;
    private boolean mCanWrite;
    private String mConfigName;
    private Context mContext;
    private String mFolderName;
    private boolean mIsLessMode;
    private boolean mIsSafety;
    private MySharedPreferences mMySP;
    private SharedPreferences mSp;
    private TransactionXMLFile mTxf;
    private SharedPreferences.Editor mEditor = null;
    private MySharedPreferences.MyEditor mMyEditor = null;

    /* JADX WARN: Removed duplicated region for block: B:101:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0154 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0170 A[Catch: Exception -> 0x017a, TRY_LEAVE, TryCatch #3 {Exception -> 0x017a, blocks: (B:79:0x016c, B:81:0x0170), top: B:94:0x016c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public PersistentConfiguration(android.content.Context r10, java.lang.String r11, java.lang.String r12, boolean r13, boolean r14) {
        /*
            Method dump skipped, instructions count: 379
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.core.persistent.PersistentConfiguration.<init>(android.content.Context, java.lang.String, java.lang.String, boolean, boolean):void");
    }

    private TransactionXMLFile getTransactionXMLFile(String str) {
        File rootFolder = getRootFolder(str);
        if (rootFolder != null) {
            TransactionXMLFile transactionXMLFile = new TransactionXMLFile(rootFolder.getAbsolutePath());
            this.mTxf = transactionXMLFile;
            return transactionXMLFile;
        }
        return null;
    }

    private File getRootFolder(String str) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        if (externalStorageDirectory != null) {
            File file = new File(String.format("%s%s%s", externalStorageDirectory.getAbsolutePath(), File.separator, str));
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        }
        return null;
    }

    private void copySPToMySP(SharedPreferences sharedPreferences, MySharedPreferences mySharedPreferences) {
        MySharedPreferences.MyEditor edit;
        if (sharedPreferences == null || mySharedPreferences == null || (edit = mySharedPreferences.edit()) == null) {
            return;
        }
        edit.clear();
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                edit.putString(key, (String) value);
            } else if (value instanceof Integer) {
                edit.putInt(key, ((Integer) value).intValue());
            } else if (value instanceof Long) {
                edit.putLong(key, ((Long) value).longValue());
            } else if (value instanceof Float) {
                edit.putFloat(key, ((Float) value).floatValue());
            } else if (value instanceof Boolean) {
                edit.putBoolean(key, ((Boolean) value).booleanValue());
            }
        }
        edit.commit();
    }

    private void copyMySPToSP(MySharedPreferences mySharedPreferences, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor edit;
        if (mySharedPreferences == null || sharedPreferences == null || (edit = sharedPreferences.edit()) == null) {
            return;
        }
        edit.clear();
        for (Map.Entry<String, ?> entry : mySharedPreferences.getAll().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                edit.putString(key, (String) value);
            } else if (value instanceof Integer) {
                edit.putInt(key, ((Integer) value).intValue());
            } else if (value instanceof Long) {
                edit.putLong(key, ((Long) value).longValue());
            } else if (value instanceof Float) {
                edit.putFloat(key, ((Float) value).floatValue());
            } else if (value instanceof Boolean) {
                edit.putBoolean(key, ((Boolean) value).booleanValue());
            }
        }
        edit.commit();
    }

    private boolean checkSDCardXMLFile() {
        MySharedPreferences mySharedPreferences = this.mMySP;
        if (mySharedPreferences != null) {
            boolean checkFile = mySharedPreferences.checkFile();
            if (!checkFile) {
                commit();
            }
            return checkFile;
        }
        return false;
    }

    private void initEditor() {
        MySharedPreferences mySharedPreferences;
        SharedPreferences sharedPreferences;
        if (this.mEditor == null && (sharedPreferences = this.mSp) != null) {
            this.mEditor = sharedPreferences.edit();
        }
        if (this.mCanWrite && this.mMyEditor == null && (mySharedPreferences = this.mMySP) != null) {
            this.mMyEditor = mySharedPreferences.edit();
        }
        checkSDCardXMLFile();
    }

    public void putInt(String str, int i) {
        if (StringUtils.isEmpty(str) || str.equals(KEY_TIMESTAMP)) {
            return;
        }
        initEditor();
        SharedPreferences.Editor editor = this.mEditor;
        if (editor != null) {
            editor.putInt(str, i);
        }
        MySharedPreferences.MyEditor myEditor = this.mMyEditor;
        if (myEditor != null) {
            myEditor.putInt(str, i);
        }
    }

    public void putLong(String str, long j) {
        if (StringUtils.isEmpty(str) || str.equals(KEY_TIMESTAMP)) {
            return;
        }
        initEditor();
        SharedPreferences.Editor editor = this.mEditor;
        if (editor != null) {
            editor.putLong(str, j);
        }
        MySharedPreferences.MyEditor myEditor = this.mMyEditor;
        if (myEditor != null) {
            myEditor.putLong(str, j);
        }
    }

    public void putBoolean(String str, boolean z) {
        if (StringUtils.isEmpty(str) || str.equals(KEY_TIMESTAMP)) {
            return;
        }
        initEditor();
        SharedPreferences.Editor editor = this.mEditor;
        if (editor != null) {
            editor.putBoolean(str, z);
        }
        MySharedPreferences.MyEditor myEditor = this.mMyEditor;
        if (myEditor != null) {
            myEditor.putBoolean(str, z);
        }
    }

    public void putFloat(String str, float f) {
        if (StringUtils.isEmpty(str) || str.equals(KEY_TIMESTAMP)) {
            return;
        }
        initEditor();
        SharedPreferences.Editor editor = this.mEditor;
        if (editor != null) {
            editor.putFloat(str, f);
        }
        MySharedPreferences.MyEditor myEditor = this.mMyEditor;
        if (myEditor != null) {
            myEditor.putFloat(str, f);
        }
    }

    public void putString(String str, String str2) {
        if (StringUtils.isEmpty(str) || str.equals(KEY_TIMESTAMP)) {
            return;
        }
        initEditor();
        SharedPreferences.Editor editor = this.mEditor;
        if (editor != null) {
            editor.putString(str, str2);
        }
        MySharedPreferences.MyEditor myEditor = this.mMyEditor;
        if (myEditor != null) {
            myEditor.putString(str, str2);
        }
    }

    public void remove(String str) {
        if (StringUtils.isEmpty(str) || str.equals(KEY_TIMESTAMP)) {
            return;
        }
        initEditor();
        SharedPreferences.Editor editor = this.mEditor;
        if (editor != null) {
            editor.remove(str);
        }
        MySharedPreferences.MyEditor myEditor = this.mMyEditor;
        if (myEditor != null) {
            myEditor.remove(str);
        }
    }

    public void reload() {
        Context context;
        if (this.mSp != null && (context = this.mContext) != null) {
            this.mSp = context.getSharedPreferences(this.mConfigName, 0);
        }
        String externalStorageState = Environment.getExternalStorageState();
        if (StringUtils.isEmpty(externalStorageState)) {
            return;
        }
        if (externalStorageState.equals("mounted") || (externalStorageState.equals("mounted_ro") && this.mMySP != null)) {
            try {
                TransactionXMLFile transactionXMLFile = this.mTxf;
                if (transactionXMLFile != null) {
                    this.mMySP = transactionXMLFile.getMySharedPreferences(this.mConfigName, 0);
                }
            } catch (Exception unused) {
            }
        }
    }

    public void clear() {
        initEditor();
        long currentTimeMillis = System.currentTimeMillis();
        SharedPreferences.Editor editor = this.mEditor;
        if (editor != null) {
            editor.clear();
            this.mEditor.putLong(KEY_TIMESTAMP, currentTimeMillis);
        }
        MySharedPreferences.MyEditor myEditor = this.mMyEditor;
        if (myEditor != null) {
            myEditor.clear();
            this.mMyEditor.putLong(KEY_TIMESTAMP, currentTimeMillis);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean commit() {
        /*
            r6 = this;
            long r0 = java.lang.System.currentTimeMillis()
            android.content.SharedPreferences$Editor r2 = r6.mEditor
            r3 = 0
            if (r2 == 0) goto L20
            boolean r4 = r6.mIsLessMode
            if (r4 != 0) goto L16
            android.content.SharedPreferences r4 = r6.mSp
            if (r4 == 0) goto L16
            java.lang.String r4 = "t"
            r2.putLong(r4, r0)
        L16:
            android.content.SharedPreferences$Editor r0 = r6.mEditor
            boolean r0 = r0.commit()
            if (r0 != 0) goto L20
            r0 = 0
            goto L21
        L20:
            r0 = 1
        L21:
            android.content.SharedPreferences r1 = r6.mSp
            if (r1 == 0) goto L31
            android.content.Context r1 = r6.mContext
            if (r1 == 0) goto L31
            java.lang.String r2 = r6.mConfigName
            android.content.SharedPreferences r1 = r1.getSharedPreferences(r2, r3)
            r6.mSp = r1
        L31:
            java.lang.String r1 = android.os.Environment.getExternalStorageState()
            boolean r2 = com.ta.utdid2.android.utils.StringUtils.isEmpty(r1)
            if (r2 != 0) goto L98
            java.lang.String r2 = "mounted"
            boolean r4 = r1.equals(r2)
            if (r4 == 0) goto L7a
            com.ta.utdid2.core.persistent.MySharedPreferences r4 = r6.mMySP
            if (r4 != 0) goto L6f
            java.lang.String r4 = r6.mFolderName
            com.ta.utdid2.core.persistent.TransactionXMLFile r4 = r6.getTransactionXMLFile(r4)
            if (r4 == 0) goto L7a
            java.lang.String r5 = r6.mConfigName
            com.ta.utdid2.core.persistent.MySharedPreferences r4 = r4.getMySharedPreferences(r5, r3)
            r6.mMySP = r4
            boolean r5 = r6.mIsLessMode
            if (r5 != 0) goto L61
            android.content.SharedPreferences r5 = r6.mSp
            r6.copySPToMySP(r5, r4)
            goto L66
        L61:
            android.content.SharedPreferences r5 = r6.mSp
            r6.copyMySPToSP(r4, r5)
        L66:
            com.ta.utdid2.core.persistent.MySharedPreferences r4 = r6.mMySP
            com.ta.utdid2.core.persistent.MySharedPreferences$MyEditor r4 = r4.edit()
            r6.mMyEditor = r4
            goto L7a
        L6f:
            com.ta.utdid2.core.persistent.MySharedPreferences$MyEditor r4 = r6.mMyEditor
            if (r4 == 0) goto L7a
            boolean r4 = r4.commit()
            if (r4 != 0) goto L7a
            r0 = 0
        L7a:
            boolean r2 = r1.equals(r2)
            if (r2 != 0) goto L8c
            java.lang.String r2 = "mounted_ro"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L98
            com.ta.utdid2.core.persistent.MySharedPreferences r1 = r6.mMySP
            if (r1 == 0) goto L98
        L8c:
            com.ta.utdid2.core.persistent.TransactionXMLFile r1 = r6.mTxf     // Catch: java.lang.Exception -> L98
            if (r1 == 0) goto L98
            java.lang.String r2 = r6.mConfigName     // Catch: java.lang.Exception -> L98
            com.ta.utdid2.core.persistent.MySharedPreferences r1 = r1.getMySharedPreferences(r2, r3)     // Catch: java.lang.Exception -> L98
            r6.mMySP = r1     // Catch: java.lang.Exception -> L98
        L98:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.core.persistent.PersistentConfiguration.commit():boolean");
    }

    public String getString(String str) {
        checkSDCardXMLFile();
        SharedPreferences sharedPreferences = this.mSp;
        if (sharedPreferences != null) {
            String string = sharedPreferences.getString(str, "");
            if (!StringUtils.isEmpty(string)) {
                return string;
            }
        }
        MySharedPreferences mySharedPreferences = this.mMySP;
        return mySharedPreferences != null ? mySharedPreferences.getString(str, "") : "";
    }

    public int getInt(String str) {
        checkSDCardXMLFile();
        SharedPreferences sharedPreferences = this.mSp;
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(str, 0);
        }
        MySharedPreferences mySharedPreferences = this.mMySP;
        if (mySharedPreferences != null) {
            return mySharedPreferences.getInt(str, 0);
        }
        return 0;
    }

    public long getLong(String str) {
        checkSDCardXMLFile();
        SharedPreferences sharedPreferences = this.mSp;
        if (sharedPreferences != null) {
            return sharedPreferences.getLong(str, 0L);
        }
        MySharedPreferences mySharedPreferences = this.mMySP;
        if (mySharedPreferences != null) {
            return mySharedPreferences.getLong(str, 0L);
        }
        return 0L;
    }

    public float getFloat(String str) {
        checkSDCardXMLFile();
        SharedPreferences sharedPreferences = this.mSp;
        if (sharedPreferences != null) {
            return sharedPreferences.getFloat(str, 0.0f);
        }
        MySharedPreferences mySharedPreferences = this.mMySP;
        if (mySharedPreferences != null) {
            return mySharedPreferences.getFloat(str, 0.0f);
        }
        return 0.0f;
    }

    public boolean getBoolean(String str) {
        checkSDCardXMLFile();
        SharedPreferences sharedPreferences = this.mSp;
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(str, false);
        }
        MySharedPreferences mySharedPreferences = this.mMySP;
        if (mySharedPreferences != null) {
            return mySharedPreferences.getBoolean(str, false);
        }
        return false;
    }

    public Map<String, ?> getAll() {
        checkSDCardXMLFile();
        SharedPreferences sharedPreferences = this.mSp;
        if (sharedPreferences != null) {
            return sharedPreferences.getAll();
        }
        MySharedPreferences mySharedPreferences = this.mMySP;
        if (mySharedPreferences != null) {
            return mySharedPreferences.getAll();
        }
        return null;
    }
}
