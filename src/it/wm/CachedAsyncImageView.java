
package it.wm;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * TODO: document your custom view class.
 */
public class CachedAsyncImageView extends ImageView implements DownloaderTask.ResponseListener {

    private static final String DEBUG_TAG = "CachedAsyncImageView";
    private DownloaderTask task = null;
    private String urlString;

    public CachedAsyncImageView(Context context) {
        super(context);
    }

    public void loadImageFromURL(URL url) {

    }

    public void loadImageFromURL(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(DEBUG_TAG, "Invalid URL:" + urlString);
            e.printStackTrace();
            return;
        }
        loadImageFromURL(url);
    }

    /*
     * - (void)loadImageFromURL:(NSURL *)url {
     * @synchronized (self) { if (_connection) { [self releaseConnection]; }
     * _urlString = [url.absoluteString retain];
     * //NSLog(@"[%@ loadImageFromUrl]  [[%@]+]", [self class], [_urlString
     * substringWithRange:NSMakeRange(_urlString.length-1-10, 3)]); UIImage
     * *image = [[ImageCache sharedInstance] imageForURLString:_urlString];
     * //NSLog(@"[%@ loadImageFromURL]: _urlString = %@", [self class],
     * _urlString); if (image) {
     * //NSLog(@"                          \t Cache Hit! [[%@]-]", [_urlString
     * substringWithRange:NSMakeRange(_urlString.length-1-10, 3)]); self.image =
     * image; [_urlString release]; _urlString = nil; } else { self.image = nil;
     * NSURLRequest *request = [NSURLRequest requestWithURL:url]; _connection =
     * [[NSURLConnection alloc] initWithRequest:request delegate:self]; if
     * (_connection) { [_activityIndicator startAnimating]; } else { [self
     * connection:nil didFailWithError:nil]; } } } } -
     * (void)loadImageFromURLString:(NSString *)urlString { NSURL *url = [NSURL
     * URLWithString:urlString]; [self loadImageFromURL:url]; } -
     * (void)setActivityIndicatorStyle:(UIActivityIndicatorViewStyle)style {
     * _activityIndicator.activityIndicatorViewStyle = style; } +
     * (void)emptyCache { [[ImageCache sharedInstance] emptyCache];
     */

    @Override
    public void onHTTPResponseReceived(DownloaderTask task, byte[] response) {
        if (task != this.task) {
            return;
        }
        Drawable image = null;
        // Non serve un synchronized: anche se questo metodo potrebbe venire
        // richiamato da due DownloaderTask diversi (a causa di una doppia
        // chiamata a loadImageFromURL) questo metodo viene eseguito sempre e
        // solo nel thread della UI, quindi non c'è bisogno di renderlo
        // ulteriormente thread safe. Il discorso poi cambia se uno inizia a
        // richiamare i metodi dell'interfaccia ResponseListener al di fuori dal
        // DownloaderTask.... ma ques
        if (new String(response).equals("Use a placeholder")) {
            // TODO: settare un placeholder
        } else {
            image = new BitmapDrawable(new ByteArrayInputStream(response));
        }
        ImageCache.getInstance().putDrawable(urlString, image);
        /*
         * [_activityIndicator stopAnimating]; self.alpha = 0; [UIView
         * beginAnimations:nil context:nil]; [UIView setAnimationDuration:1.0];
         * self.image = image; self.alpha = 1; [UIView commitAnimations];
         * if(delegate && [delegate
         * respondsToSelector:@selector(didFinishLoadingImage:)]) [delegate
         * didFinishLoadingImage:self];
         */

    }

    @Override
    public void onHTTPerror(DownloaderTask task) {
        /*- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error {
            // Settare un immagine placeholder?
            @synchronized (self) {
                if (connection != _connection) return;
                [_activityIndicator stopAnimating];
                [self releaseConnection];
            }
            
            if(delegate && [delegate respondsToSelector:@selector(didErrorLoadingImage:)])
                [delegate didErrorLoadingImage:self];       
         */
    }

    private static class ImageCache {
        private static ImageCache __instance = null;
        private HashMap<String, Drawable> cache = null;

        private ImageCache() {
            cache = new HashMap<String, Drawable>();
        }

        public static ImageCache getInstance() {
            if (__instance == null) {
                __instance = new ImageCache();
            }
            return __instance;
        }

        public Drawable getDrawable(String urlString) {
            return cache.get(urlString);
        }

        public void putDrawable(String urlString, Drawable drawable) {
            cache.put(urlString, drawable);
        }

        public void emptyCache() {
            cache.clear();
        }

    }
}
