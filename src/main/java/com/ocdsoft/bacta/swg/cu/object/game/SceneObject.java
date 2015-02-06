package com.ocdsoft.bacta.swg.cu.object.game;


import com.ocdsoft.bacta.engine.lang.ObservableEventRegistry;
import com.ocdsoft.bacta.engine.lang.Observer;
import com.ocdsoft.bacta.engine.lang.Subject;
import com.ocdsoft.bacta.engine.object.NetworkObject;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.cu.event.ObservableGameEvent;
import com.ocdsoft.bacta.swg.cu.message.game.server.SceneCreateObjectByCrc;
import com.ocdsoft.bacta.swg.cu.message.game.server.SceneDestroyObject;
import com.ocdsoft.bacta.swg.cu.message.game.server.SceneEndBaselines;
import com.ocdsoft.bacta.swg.cu.message.game.server.UpdateContainmentMessage;
import com.ocdsoft.bacta.swg.shared.container.Container;
import com.ocdsoft.bacta.swg.shared.object.template.ObjectTemplate;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public abstract class SceneObject extends NetworkObject implements Subject<ObservableGameEvent> {

    private static final transient Logger logger = LoggerFactory.getLogger(SceneObject.class);

    public int getOpcode() {
        return 0x53434E4F;
    } //'SCNO'

    @Getter
    private transient boolean initialized = false;

    @Getter
    @Setter
    private long containedBy = 0;

    @Getter
    @Setter
    private Transform transform;
    
    @Getter
    @Setter
    private boolean hyperspace;

    protected transient Container<SceneObject> container;

    @Getter
    @Setter
    protected int currentArrangement = -1;
    
    @Getter
    @Setter
    protected transient SoeUdpConnection connection;
    
    protected transient final Set<SoeUdpConnection> listeners;

    @Getter
    @Setter //TODO: Remove setter access. Require reflection to gain access.
    private transient ObjectTemplate objectTemplate;

//    @Getter protected transient final AutoDeltaByteStream authoritativeClientServerPackage = new AutoDeltaByteStream(this); //1
//    @Getter protected transient final AutoDeltaByteStream authoritativeClientServerPackageNp = new AutoDeltaByteStream(this); //4
//    @Getter protected transient final AutoDeltaByteStream firstParentAuthClientServerPackage = new AutoDeltaByteStream(this); //8
//    @Getter protected transient final AutoDeltaByteStream firstParentAuthClientServerPackageNp = new AutoDeltaByteStream(this); //9
//    @Getter protected transient final AutoDeltaByteStream sharedPackage = new AutoDeltaByteStream(this); //3
//    @Getter protected transient final AutoDeltaByteStream sharedPackageNp = new AutoDeltaByteStream(this); //6
//    @Getter protected transient final AutoDeltaByteStream uiPackage = new AutoDeltaByteStream(this); //7

//    private final AutoDeltaInt balanceBank;
//    private final AutoDeltaInt balanceCash;
//
//    private final AutoDeltaFloat complexity;
//    private final AutoDeltaVariable<StringId> nameStringId;
//    private final AutoDeltaVariable<UnicodeString> objectName;
//    private final AutoDeltaInt volume;
//
//    private final AutoDeltaInt authServerProcessId;

//    public final int getBalanceBank() { return balanceBank.get(); }
//    public final void setBalanceBank(int value) { balanceBank.set(value); setDirty(true); }
//
//    public final int getBalanceCash() { return balanceCash.get(); }
//    public final void setBalanceCash(int value) { balanceCash.set(value); setDirty(true); }
//
//    public final float getComplexity() { return complexity.get(); }
//    public final void setComplexity(float value) { complexity.set(value); setDirty(true); }
//
//    public final StringId getNameStringId() { return nameStringId.get(); }
//    public final void setNameStringId(StringId value) { nameStringId.set(value); setDirty(true); }
//
//    public final UnicodeString getObjectName() { return objectName.get(); }
//    public final void setObjectName(UnicodeString value) { objectName.set(value); setDirty(true); }
//
//    public final int getVolume() { return volume.get(); }
//    public final void setVolume(int value) { volume.set(value); setDirty(true); }
//
//    public final int getAuthServerProcessId() { return authServerProcessId.get(); }
//    public final void setAuthServerProcessId(int value) { authServerProcessId.set(value); setDirty(true); }

    protected final transient ObservableEventRegistry<ObservableGameEvent> eventRegistry;

    protected SceneObject() {
        listeners = Collections.synchronizedSet(new HashSet<>());
        connection = null;
        transform = new Transform();

//        balanceBank = new AutoDeltaInt(0, authoritativeClientServerPackage);
//        balanceCash = new AutoDeltaInt(0, authoritativeClientServerPackage);
//
//        complexity = new AutoDeltaFloat(1.0f, sharedPackage);
//        nameStringId = new AutoDeltaVariable<>(StringId.empty, sharedPackage);
//        objectName = new AutoDeltaVariable<>(UnicodeString.EMPTY, sharedPackage);
//        volume = new AutoDeltaInt(0, sharedPackage);
//
//        authServerProcessId = new AutoDeltaInt(0, sharedPackageNp);

        eventRegistry = new ObservableEventRegistry();
    }

    public final void sendTo(SoeUdpConnection theirConnection) {
        logger.trace("Sending baselines to {}.", getNetworkId());

        if (theirConnection == null) return;

        SceneCreateObjectByCrc msg = new SceneCreateObjectByCrc(this);
        theirConnection.sendMessage(msg);

        UpdateContainmentMessage link = new UpdateContainmentMessage(this, containedBy, currentArrangement);
        theirConnection.sendMessage(link);

//        sendBaselinesTo(theirConnection);

        if (container != null) {
            for (SceneObject containedObject : container) {
                //TODO: Remove this if block when refactored to have contents list on Container.
                //At that point, there should never be a null object in the list...
                if (containedObject != null) {
                    containedObject.sendTo(theirConnection);
                }
            }
        }

        SceneEndBaselines close = new SceneEndBaselines(this);
        theirConnection.sendMessage(close);
    }

    public final void sendDestroyTo(SoeUdpConnection theirConnection) {
        if (theirConnection != null) {
            SceneDestroyObject msg = new SceneDestroyObject(this);
            theirConnection.sendMessage(msg);
        }
    }

//    public final void setInitialized() {
//        clearDeltas(); //Clear any deltas that might've been set before initialization.
//
//        initialized = true;
//    }

//    private final void sendBaselinesTo(GameConnection theirConnection) {
//
//        if(this.connection == null || theirConnection == null) return;
//
//        if (this.connection.equals(theirConnection)) {
//            theirConnection.sendMessage(new BaselinesMessage(this, authoritativeClientServerPackage, 1)); //BaselineTypes.ClientServer)); //1
//        }
//
//        theirConnection.sendMessage(new BaselinesMessage(this, sharedPackage, 3)); //BaselineTypes.Shared)); //3
//
//        if (this.connection.equals(theirConnection)) {
//            theirConnection.sendMessage(new BaselinesMessage(this, authoritativeClientServerPackageNp, 4)); //BaselineTypes.ClientServerNp)); //4
//        }
//
//        theirConnection.sendMessage(new BaselinesMessage(this, sharedPackageNp, 6)); //BaselineTypes.SharedNp)); //6
//
//        if (this.connection.equals(theirConnection)) {
//            theirConnection.sendMessage(new BaselinesMessage(this, uiPackage, 7)); //BaselineTypes.Ui)); //7
//            theirConnection.sendMessage(new BaselinesMessage(this, firstParentAuthClientServerPackage, 8)); //BaselineTypes.FirstParentClientServer)); //8
//            theirConnection.sendMessage(new BaselinesMessage(this, firstParentAuthClientServerPackageNp, 9)); //BaselineTypes.FirstParentClientServerNp)); //9
//        }
//    }
//
//    public final void sendDeltas() {
//        if (authoritativeClientServerPackage.isDirty())
//            broadcastMessage(new DeltasMessage(this, authoritativeClientServerPackage, 1));
//
//        if (sharedPackage.isDirty())
//            connection.sendMessage(new DeltasMessage(this, sharedPackage, 3));
//
//        if (authoritativeClientServerPackageNp.isDirty())
//            connection.sendMessage(new DeltasMessage(this, authoritativeClientServerPackageNp, 4));
//
//        if (sharedPackageNp.isDirty())
//            broadcastMessage(new DeltasMessage(this, sharedPackageNp, 6));
//
//        if (uiPackage.isDirty())
//            connection.sendMessage(new DeltasMessage(this, uiPackage, 7));
//
//        if (firstParentAuthClientServerPackage.isDirty())
//            connection.sendMessage(new DeltasMessage(this, firstParentAuthClientServerPackage, 8));
//
//        if (firstParentAuthClientServerPackageNp.isDirty())
//            connection.sendMessage(new DeltasMessage(this, firstParentAuthClientServerPackageNp, 9));
//
//        clearDeltas();
//    }
//
//    public final void clearDeltas() {
//        authoritativeClientServerPackage.clearDeltas();
//        authoritativeClientServerPackageNp.clearDeltas();
//        sharedPackage.clearDeltas();
//        sharedPackageNp.clearDeltas();
//        uiPackage.clearDeltas();
//        firstParentAuthClientServerPackage.clearDeltas();
//        firstParentAuthClientServerPackageNp.clearDeltas();
//    }
//
//    public void setPosition(float x, float z, float y) {
//        position.x = x;
//        position.y = y;
//        position.z = z;
//        dirty = true;
//    }

//    public void setPosition(float x, float z, float y, boolean updateZone) {
//        setPosition(x, z, y);
//    }
//
//    public void setPosition(Vec3 position) {
//        this.position = position;
//        dirty = true;
//    }
//
//    public final void setOrientation(float x, float y, float z, float w) {
//        orientation.set(x, y, z, w);
//        dirty = true;
//    }
//
//    public final void setOrientation(Quat4f orientation) {
//        this.orientation = orientation;
//        dirty = true;
//    }
//
//    public void setOnDirtyCallback(OnDirtyCallbackBase onDirtyCallback) {
//        sharedPackage.addOnDirtyCallback(onDirtyCallback);
//        sharedPackageNp.addOnDirtyCallback(onDirtyCallback);
//        authoritativeClientServerPackage.addOnDirtyCallback(onDirtyCallback);
//        authoritativeClientServerPackageNp.addOnDirtyCallback(onDirtyCallback);
//        firstParentAuthClientServerPackage.addOnDirtyCallback(onDirtyCallback);
//        firstParentAuthClientServerPackageNp.addOnDirtyCallback(onDirtyCallback);
//        uiPackage.addOnDirtyCallback(onDirtyCallback);
//    }

    public final void broadcastMessage(GameNetworkMessage message) {
//        broadcastMessage(message, true);
    }
//
//    public final void broadcastMessage(SwgMessage message, boolean sendSelf) {
//
//        for (GameClient theirClient : listeners) {
//            if(!sendSelf && theirClient == client) {
//                continue;
//            }
//            SwgMessage newMessage = new SwgMessage(message.copy());
//            theirClient.sendMessage(newMessage);
//            logger.debug("Broadcasting message " + message.getClass().getSimpleName() + " to " + theirClient.getCharacter().getObjectName().getString());
//            System.out.println(SoeMessageUtil.bytesToHex(newMessage));
//        }
//        message.release();
//    }
//
//    public final void broadcastMessage(ObjControllerMessage message) {
//        broadcastMessage(message, false);
//    }
//
//    public final void broadcastMessage(ObjControllerMessage message, boolean changeReceiver) {
//        for (GameClient theirClient : listeners) {
//            ObjControllerMessage newMessage = new ObjControllerMessage(message.copy());
//            if(changeReceiver) {
//                newMessage.setReceiver(theirClient.getCharacter().getNetworkId());
//            }
//            theirClient.sendMessage(newMessage);
//
//            logger.debug("Broadcasting obj controller to " + theirClient.getCharacter().getObjectName().getString());
//            System.out.println(SoeMessageUtil.bytesToHex(newMessage));
//        }
//
//        message.release();
//    }

    @Override
    public final void register(Observer obj, ObservableGameEvent event) {
        eventRegistry.register(obj, event);
    }

    @Override
    public final void unregister(Observer obj, ObservableGameEvent event) {
        eventRegistry.unregister(obj, event);
    }

    @Override
    public void notifyObservers(ObservableGameEvent event) {
        eventRegistry.notifyObservers(event);
    }

    @Override
    public Object getUpdate(Observer obj) {
        return null;
    }
}
