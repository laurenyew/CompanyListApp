package bottlerocket.laurenyew.companylist.list;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.lang.ref.WeakReference;

import bottlerocket.laurenyew.companylist.services.LoadLogoBitmapAsyncTask;

public class DownloadedLogoDrawable extends ColorDrawable {
    private final WeakReference<LoadLogoBitmapAsyncTask> loadLogoBitmapAsyncTaskReference;

    public DownloadedLogoDrawable(LoadLogoBitmapAsyncTask logoDownloadertask) {
        super(Color.GRAY);
        loadLogoBitmapAsyncTaskReference =
            new WeakReference<>(logoDownloadertask);
    }

    public LoadLogoBitmapAsyncTask getLoadLogoBitmapAsyncTask() {
        return loadLogoBitmapAsyncTaskReference.get();
    }
}