package gc.cordova.libs.test_r;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.Exception;
import java.io.File;

/**
 * Detect weather device is rooted or not
 * @author trykov
 */
public class GCTest extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("checkDevice")) {
            try {
                callbackContext.success(checkDevice() ? 1 : 0);
                return true;
            } catch (Exception e) {
                callbackContext.error("N/A");
                return false;
            }
        }
        return false;
    }

    private boolean checkDevice() {
        return checkBuildTags() || checkSuperUserApk() || checkFilePath();
    }
    private boolean checkBuildTags() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private boolean checkSuperUserApk() {
        return new File("/system/app/Superuser.apk").exists();
    }

    private String check(String s){
        byte[] item = s.getBytes();
        byte[] decodedBytes = Base64.getDecoder().decode(item);
        return new String(decodedBytes);
    }

    private boolean checkFilePath() {
        String[] paths = { check("L3NiaW4vc3U="), check("L3N5c3RlbS9iaW4vc3U="), check("L3N5c3RlbS94YmluL3N1"), check("L2RhdGEvbG9jYWwveGJpbi9zdQ=="), check("L2RhdGEvbG9jYWwvYmluL3N1"),
                check("L3N5c3RlbS9zZC94YmluL3N1"), check("L3N5c3RlbS9iaW4vZmFpbHNhZmUvc3U="), check("L2RhdGEvbG9jYWwvc3U="), check("L2RhdGEvbG9jYWwvYmluLw=="),
                check("L2RhdGEvbG9jYWwveGJpbi8="), check("L3N5c3RlbS9iaW4="), check("L3N5c3RlbS9zYmlu"), check("L3N5c3RlbS94Ymlu"), check("L3ZlbmRvci9iaW4="), check("L3NiaW4="), check("L2V0Yw==") };
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }
}
