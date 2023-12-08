package com.ccr.achenglibrary.photopicker.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import com.ccr.achenglibrary.photopicker.util.CCRAsyncTask;
import java.io.File;
import java.lang.ref.SoftReference;

/* loaded from: classes.dex */
public class CCRSavePhotoTask extends CCRAsyncTask<Void, Void> {
    private SoftReference<Bitmap> mBitmap;
    private Context mContext;
    private File mNewFile;

    public CCRSavePhotoTask(CCRAsyncTask.Callback<Void> callback, Context context, File file) {
        super(callback);
        this.mContext = context.getApplicationContext();
        this.mNewFile = file;
    }

    public void setBitmapAndPerform(Bitmap bitmap) {
        this.mBitmap = new SoftReference<>(bitmap);
        if (Build.VERSION.SDK_INT >= 11) {
            executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        } else {
            execute(new Void[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v0, types: [int] */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r0v10, types: [java.io.OutputStream, java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r1v4, types: [android.graphics.Bitmap] */
    @Override // android.os.AsyncTask
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Void doInBackground(java.lang.Void... r8) {
        /*
            r7 = this;
            r8 = 0
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L43
            java.io.File r1 = r7.mNewFile     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L43
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L43
            java.lang.ref.SoftReference<android.graphics.Bitmap> r1 = r7.mBitmap     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L58
            java.lang.Object r1 = r1.get()     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L58
            android.graphics.Bitmap r1 = (android.graphics.Bitmap) r1     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L58
            android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.PNG     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L58
            r3 = 100
            r1.compress(r2, r3, r0)     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L58
            r0.flush()     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L58
            android.content.Context r1 = r7.mContext     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L58
            int r2 = com.ccr.achenglibrary.R.string.bga_pp_save_img_success_folder     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L58
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L58
            r4 = 0
            java.io.File r5 = r7.mNewFile     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L58
            java.io.File r5 = r5.getParentFile()     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L58
            java.lang.String r5 = r5.getAbsolutePath()     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L58
            r3[r4] = r5     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L58
            java.lang.String r1 = r1.getString(r2, r3)     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L58
            com.ccr.achenglibrary.photopicker.util.CCRPhotoPickerUtil.showSafe(r1)     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L58
            java.io.File r1 = r7.mNewFile     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L58
            r7.updateImg(r1)     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L58
            r0.close()     // Catch: java.io.IOException -> L4f
            goto L54
        L3e:
            r0 = move-exception
            r6 = r0
            r0 = r8
            r8 = r6
            goto L59
        L43:
            r0 = r8
        L44:
            int r1 = com.ccr.achenglibrary.R.string.bga_pp_save_img_failure     // Catch: java.lang.Throwable -> L58
            com.ccr.achenglibrary.photopicker.util.CCRPhotoPickerUtil.showSafe(r1)     // Catch: java.lang.Throwable -> L58
            if (r0 == 0) goto L54
            r0.close()     // Catch: java.io.IOException -> L4f
            goto L54
        L4f:
            int r0 = com.ccr.achenglibrary.R.string.bga_pp_save_img_failure
            com.ccr.achenglibrary.photopicker.util.CCRPhotoPickerUtil.showSafe(r0)
        L54:
            r7.recycleBitmap()
            return r8
        L58:
            r8 = move-exception
        L59:
            if (r0 == 0) goto L64
            r0.close()     // Catch: java.io.IOException -> L5f
            goto L64
        L5f:
            int r0 = com.ccr.achenglibrary.R.string.bga_pp_save_img_failure
            com.ccr.achenglibrary.photopicker.util.CCRPhotoPickerUtil.showSafe(r0)
        L64:
            r7.recycleBitmap()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ccr.achenglibrary.photopicker.util.CCRSavePhotoTask.doInBackground(java.lang.Void[]):java.lang.Void");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ccr.achenglibrary.photopicker.util.CCRAsyncTask, android.os.AsyncTask
    public void onCancelled() {
        super.onCancelled();
        recycleBitmap();
    }

    private void recycleBitmap() {
        SoftReference<Bitmap> softReference = this.mBitmap;
        if (softReference == null || softReference.get() == null || this.mBitmap.get().isRecycled()) {
            return;
        }
        this.mBitmap.get().recycle();
        this.mBitmap = null;
    }

    private void updateImg(File file) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(file));
        this.mContext.sendBroadcast(intent);
        Toast.makeText(this.mContext, "更新啦", 0).show();
        Log.d("Acheng", "更新啦");
    }
}
