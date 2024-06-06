package com.harshitha.rudderstackandroidsample;

import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.clevertap.android.sdk.CleverTapAPI;
import com.rudderstack.android.integrations.clevertap.CleverTapIntegrationFactory;
import com.rudderstack.android.sdk.core.RudderClient;
import com.rudderstack.android.sdk.core.RudderConfig;
import com.rudderstack.android.sdk.core.RudderLogger;
import com.rudderstack.android.sdk.core.RudderProperty;
import com.rudderstack.android.sdk.core.RudderTraits;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    RudderClient crudderClient;
    // int order_id,checkout_id;
    // float revenue, total, tax,shipping, discount;
    // String affiliation,coupon, currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RudderClient Instance
        crudderClient = RudderClient.getInstance(
                this, "2IFSsNNMfIFXBeQ3Qrh2FZuzHSJ",
                new RudderConfig.Builder()
                        .withDataPlaneUrl("https://clevertaprqui.dataplane.rudderstack.com")
                        .withTrackLifecycleEvents(true)
                        .withLogLevel(RudderLogger.RudderLogLevel.DEBUG)
                        .withFactory(CleverTapIntegrationFactory.FACTORY)
                        .build()
        );

        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);

        //Notification Channel creation
        CleverTapAPI.createNotificationChannel(getApplicationContext(),"RudderAndroid","RudderAndroid","Rudder Stack Notification Channel", NotificationManager.IMPORTANCE_MAX,true);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Button userProfileButton = (Button) findViewById(R.id.PushUserProfile);
        userProfileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pushUserProfile();
            }
        });

        Button profilePushButton = (Button) findViewById(R.id.PushEvents);
        profilePushButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pushBasicEvents();
            }
        });

        Button becomingCustomerButton = (Button) findViewById(R.id.OrderCompletedEvent);
        becomingCustomerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                orderCompletedEvent();
            }
        });
    }

    private void pushUserProfile() {
        RudderTraits traits = new RudderTraits();
        traits.putBirthday(new Date());
        traits.putEmail("tstharsh@gmail.com");
        traits.putFirstName("Harshitha");
        traits.putLastName("M");
        traits.putGender("f");
        traits.putPhone("5555555555");
        traits.put("boolean", Boolean.TRUE);
        traits.put("integer", 50);
        traits.put("float", 120.4f);
        traits.put("long", 1234L);
        traits.put("string", "hello");
        traits.put("date", new Date(System.currentTimeMillis()));

        crudderClient.identify("test_user_id1", traits, null);
        Toast.makeText(getApplicationContext(), "User Profile Pushed", Toast.LENGTH_LONG).show();
    }

    private void pushBasicEvents() {
        crudderClient.track(
                "Product Clicked", new RudderProperty().putValue("product_id", "product_001")
        );
        Toast.makeText(getApplicationContext(), "Test Event", Toast.LENGTH_LONG).show();
    }

    private void orderCompletedEvent() {

        RudderProperty property = new RudderProperty();
        property.put("Product Name", "Casio Chronograph Watch");
        property.put("Category", "Mens Accessories");
        property.put("Price", 59.99);
        property.put("Date", new java.util.Date());
        crudderClient.track("Order Completed",property);

        Toast.makeText(getApplicationContext(), "Order Completed", Toast.LENGTH_LONG).show();
    }
}