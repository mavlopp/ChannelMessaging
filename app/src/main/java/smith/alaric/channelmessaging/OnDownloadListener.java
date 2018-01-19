package smith.alaric.channelmessaging;

/**
 * Created by smithal on 19/01/2018.
 */
public interface OnDownloadListener {
    void onDownloadComplete(String downloadedContent);
    void onDownloadError(String error);
}
