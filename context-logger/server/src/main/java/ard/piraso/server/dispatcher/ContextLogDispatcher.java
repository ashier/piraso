package ard.piraso.server.dispatcher;

import ard.piraso.api.Level;
import ard.piraso.api.entry.Entry;
import ard.piraso.api.entry.MessageEntry;
import ard.piraso.server.GroupChainId;
import ard.piraso.server.PirasoContext;
import ard.piraso.server.PirasoContextHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Singleton class for dispatching log entries to monitors.
 * <p>
 * This supports forward dispatch listeners.
 */
public class ContextLogDispatcher {

    private static final ContextLogDispatcher DISPATCHER = new ContextLogDispatcher();

    /**
     * Forward log {@link ard.piraso.api.entry.MessageEntry} for dispatch.
     *
     * @param message the message
     */
    public static void forward(String message) {
        forward(new GroupChainId(String.valueOf(System.currentTimeMillis())), new MessageEntry(message));
    }

    /**
     * Forward log entry for dispatch.
     *
     * @param id the entry traceable id
     * @param entry the log entry to be dispatched.
     */
    public static void forward(GroupChainId id, Entry entry) {
        DISPATCHER.forwardEntry(Level.ALL, id, entry);
    }

    /**
     * Forward log entry for dispatch.
     *
     * @param level log level
     * @param id the entry traceable id
     * @param entry the log entry to be dispatched.
     */
    public static void forward(Level level, GroupChainId id, Entry entry) {
        DISPATCHER.forwardEntry(level, id, entry);
    }

    public static void addListener(DispatcherForwardListener listener) {
        DISPATCHER.addDispatcherListener(listener);
    }

    public static void removeListener(DispatcherForwardListener listener) {
        DISPATCHER.removeDispatcherListener(listener);
    }

    public static void clearListeners() {
        DISPATCHER.clearDispatcherListeners();
    }

    public static List<DispatcherForwardListener> getListeners() {
        return DISPATCHER.getDispatcherListeners();
    }

    private List<DispatcherForwardListener> listeners = Collections.synchronizedList(new LinkedList<DispatcherForwardListener>());

    /**
     * Private constructor to not allow to instantiate this class.
     */
    private ContextLogDispatcher() {}

    /**
     * Forward log entry for dispatch.
     *
     * @param level the log level
     * @param id the entry traceable id
     * @param entry the log entry to be dispatched.
     */
    public void forwardEntry(Level level, GroupChainId id, Entry entry) {
        PirasoContext context = PirasoContextHolder.getContext();

        if(context != null) {
            context.log(level, id, entry);
            fireForwardedEvent(id, entry);
        }
    }

    public void fireForwardedEvent(GroupChainId id, Entry entry) {
        DispatcherForwardEvent evt = new DispatcherForwardEvent(this, entry, id);

        List<DispatcherForwardListener> tmp = new ArrayList<DispatcherForwardListener>(listeners);
        for(DispatcherForwardListener listener : tmp) {
            listener.forwarded(evt);
        }
    }

    public List<DispatcherForwardListener> getDispatcherListeners() {
        return listeners;
    }

    public void addDispatcherListener(DispatcherForwardListener listener) {
        listeners.add(listener);
    }

    public void removeDispatcherListener(DispatcherForwardListener listener) {
        listeners.remove(listener);
    }

    public void clearDispatcherListeners() {
        listeners.clear();
    }
}
