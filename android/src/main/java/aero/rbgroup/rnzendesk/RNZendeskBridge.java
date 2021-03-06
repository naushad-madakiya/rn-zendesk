package aero.rbgroup.rnzendesk;

import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Locale;

import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

import zendesk.commonui.UiConfig;
import zendesk.core.Zendesk;
import zendesk.core.Identity;
import zendesk.core.JwtIdentity;
import zendesk.core.AnonymousIdentity;
import zendesk.support.Support;
import zendesk.support.guide.HelpCenterActivity;
import zendesk.support.request.RequestActivity;
import zendesk.support.requestlist.RequestListActivity;

public class RNZendeskBridge extends ReactContextBaseJavaModule {

    public RNZendeskBridge(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        // module name
        return "RNZendesk";
    }

    // Initialization Methods

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @ReactMethod
    public void initialize(ReadableMap config) {
        String appId = config.getString("appId");
        String zendeskUrl = config.getString("zendeskUrl");
        String clientId = config.getString("clientId");
        Zendesk.INSTANCE.init(getReactApplicationContext(), zendeskUrl, appId, clientId);
        Support.INSTANCE.init(Zendesk.INSTANCE);
    }

    // Indentification Methods

    @ReactMethod
    public void identifyAnonymous(String name, String email) {
        Identity identity = new AnonymousIdentity.Builder()
            .withNameIdentifier(name)
            .withEmailIdentifier(email)
            .build();

        Zendesk.INSTANCE.setIdentity(identity);
    }

    // UI Methods

    @ReactMethod
    public void showHelpCenter(ReadableMap options) {
        ArrayList tags = options.hasKey("tags") ? options.getArray("tags").toArrayList() : new ArrayList();
        String subject = options.hasKey("subject") ? options.getString("subject") : "";
        boolean enableContactUs = !(options.hasKey("hideContactSupport") && options.getBoolean("hideContactSupport"));

        UiConfig requestActivityConfig = RequestActivity.builder()
            .withRequestSubject(subject)
            .withTags(tags)
            .config();

        Intent hcaIntent = HelpCenterActivity.builder()
            .withContactUsButtonVisible(enableContactUs)
            .intent(getReactApplicationContext(), requestActivityConfig);

        hcaIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getReactApplicationContext().startActivity(hcaIntent);
    }
}
