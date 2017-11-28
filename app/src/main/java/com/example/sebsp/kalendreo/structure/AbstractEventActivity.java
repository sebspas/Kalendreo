package com.example.sebsp.kalendreo.structure;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.example.sebsp.kalendreo.MainActivity;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.components.listeners.EventLoadedListener;
import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.utils.Tag;

/**
 * Created by Gaetan on 17/11/2017.
 * Generic event activity : activity where a specified event is involved
 */
public abstract class AbstractEventActivity extends AbstractLoggedInActivity {

    protected EventLoadedListener listener;
    protected ViewGroup parentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = new EventLoadedListener(this);
        parentContainer = findViewById(R.id.parent_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String idEvent = getStringFromIntent(R.string.EXTRA_EVENT_ID);
        if (idEvent == null) {
            this.errorGettingEvent();
        }
        displayProgressBar(parentContainer);
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
    public void onEventLoaded(Event event) {
        hideProgressBar();
    }

    /**
     * End this view in case of error
     */
    public void errorGettingEvent() {
        hideProgressBar();
        Log.e(Tag.INTENT_ERROR, "Could not get id event from intent");
        launchAndClose(new Intent(this, MainActivity.class));
    }

}
