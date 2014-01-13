package net.euroboxonline.battlecom;

import android.app.Application;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by draw on 13/01/14.
 */
public class BattleComApplication extends Application
{

    Map<String, WeakReference<Object>> data = new HashMap<String, WeakReference<Object>>();

    public void save( String id, Object object )
    {
        data.put(id, new WeakReference<Object>(object));
    }

    public Object retrieve(String id)
    {
        WeakReference<Object> objectWeakReference = data.get(id);
        return objectWeakReference.get();
    }

}
