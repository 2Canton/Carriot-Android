package com.nansoft.mipuribus.helper;


/**
 * Created by itadmin on 3/13/16.
 */

public class CustomDataListener
{
    public interface DataListener {

        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        public void onObjectReady(String title);
        // or when data has been loaded
        public void onDataLoaded(boolean data);

    }

    DataListener dataListener;

    public CustomDataListener()
    {
        dataListener = null;
    }

    // Assign the listener implementing events interface that will receive the events
    public void setCustomObjectListener(DataListener listener) {
        dataListener = listener;
    }
}


