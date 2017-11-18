package com.example.sebsp.kalendreo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.utils.Tag;

/**
 * Created by Gaetan on 17/11/2017.
 * Generic event activity : activity where a specified event is involved
 */
public abstract class AbstractEventActivity extends AbstractLoggedInActivity {

    protected EventLoadedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = new EventLoadedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String idEvent = getStringFromIntent(R.string.EXTRA_EVENT_ID);
        if (idEvent == null) {
            this.errorGettingEvent();
        }
        // TODO Display Progress
        loadEvent(idEvent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Event.getReference(firebaseUser.getUid()).removeEventListener(listener);
    }

    protected abstract void loadEvent(String idEvent);

    /**
     * Called when event is loaded
     *
     * @param event event loaded
     */
    protected void onEventLoaded(Event event) {
        // TODO Hide Progress
    }

    /**
     * End this view in case of error
     */
    protected void errorGettingEvent() {
        Log.e(Tag.INTENT_ERROR, "Could not get id event from intent");
        Toast.makeText(this, getText(R.string.can_not_load_event), Toast.LENGTH_SHORT).show();
        launchAndClose(new Intent(this, MainActivity.class));
    }
}
